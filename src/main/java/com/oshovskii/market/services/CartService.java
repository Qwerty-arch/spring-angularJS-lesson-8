package com.oshovskii.market.services;

import com.oshovskii.market.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.market.model.Cart;
import com.oshovskii.market.model.CartItem;
import com.oshovskii.market.model.Product;
import com.oshovskii.market.model.User;
import com.oshovskii.market.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> findById(UUID id) {
        return cartRepository.findById(id);
    }

    @Transactional
    public void addToCart(UUID cartId, Long productId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        CartItem cartItem = cart.getItemByProductId(productId);
        if (cartItem != null) {
            cartItem.incrementQuantity();
            cart.recalculate();
            return;
        }
        Product p = productService.findProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Unable to add product with id: " + productId + " to cart. Product doesn't exist"));
        cart.add(new CartItem(p));
    }

    @Transactional
    public void clearCart(UUID cartId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        cart.clear();
    }

    public Optional<Cart> findByUserId(Long id) {
        return cartRepository.findByUserId(id);
    }

    @Transactional
    public UUID getCartForUser(String username, UUID cartUuid) {
        if (username != null && cartUuid != null) {
            User user = userService.findByUsername(username).get();
            Cart cart = findById(cartUuid).get();
            Optional<Cart> oldCart = findByUserId(user.getId());
            if (oldCart.isPresent()) {
                cart.merge(oldCart.get());
                cartRepository.delete(oldCart.get());
            }
            cart.setUser(user);
        }
        if (username == null) {
            Cart cart = save(new Cart());
            return cart.getId();
        }
        User user = userService.findByUsername(username).get();
        Optional<Cart> cart = findByUserId(user.getId());
        if (cart.isPresent()) {
            return cart.get().getId();
        }
        Cart newCart = new Cart();
        newCart.setUser(user);
        save(newCart);
        return newCart.getId();
    }
}
