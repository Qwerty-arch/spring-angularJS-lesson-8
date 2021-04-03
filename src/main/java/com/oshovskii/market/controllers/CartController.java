package com.oshovskii.market.controllers;

import com.oshovskii.market.dto.CartDto;
import com.oshovskii.market.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.market.model.Cart;
import com.oshovskii.market.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public UUID createNewCart(Principal principal) {
        if (principal == null) {
            return cartService.getCartForUser(null, null);
        }
        return cartService.getCartForUser(principal.getName(), null);
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable UUID uuid) {
        Cart cart = cartService.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + uuid));
        return new CartDto(cart);
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestParam UUID uuid, @RequestParam(name = "product_id") Long productId) {
        cartService.addToCart(uuid, productId);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestParam UUID uuid) {
        cartService.clearCart(uuid);
    }
}
