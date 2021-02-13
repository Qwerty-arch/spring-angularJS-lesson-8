package com.oshovskii.market.services;

import com.oshovskii.market.dto.JwtRequest;
import com.oshovskii.market.model.Role;
import com.oshovskii.market.model.User;
import com.oshovskii.market.repositories.RoleRepository;
import com.oshovskii.market.repositories.UserRepository;
import com.oshovskii.market.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public Optional<User> findByUsername(String username) {
        log.info("IN findByUsername - user: {} found by username: {}", userRepository.findByUsername(username), username);
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
       // log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    public User register(JwtRequest registrationRequest) {
        User newUser = new User();
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setRoles(userRoles);

        final DataBinder dataBinder = new DataBinder(newUser);
        dataBinder.addValidators(userValidator);
        dataBinder.validate();

        User registeredUser = userRepository.save(newUser);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }
}