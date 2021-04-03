package com.oshovskii.market.services;

import com.oshovskii.market.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.market.model.Cart;
import com.oshovskii.market.model.Order;
import com.oshovskii.market.model.User;
import com.oshovskii.market.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;

    @Transactional
    public Order createFromUserCart(String username, UUID cartUuid, String address) {
        User user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartService.findById(cartUuid).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Order order = new Order(cart, user, address);
        order = orderRepository.save(order);
        return order;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAllOrdersByOwnerName(String username) {
        return orderRepository.findAllByOwnerUsername(username);
    }
}

