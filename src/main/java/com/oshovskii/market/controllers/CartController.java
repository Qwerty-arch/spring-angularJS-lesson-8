package com.oshovskii.market.controllers;

import com.oshovskii.market.beans.Cart;
import com.oshovskii.market.dto.CartDto;
import com.oshovskii.market.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.market.model.User;
import com.oshovskii.market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;
    private final UserService userService;

    @GetMapping
    public CartDto getCart() {
        return new CartDto(cart);
    }

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        if (!user.getRoles().isEmpty()){

        }
        cart.addToCart(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cart.clear();
    }
}

