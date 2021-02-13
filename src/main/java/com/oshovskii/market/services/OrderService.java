package com.oshovskii.market.services;

import com.oshovskii.market.beans.Cart;
import com.oshovskii.market.model.Order;
import com.oshovskii.market.model.User;
import com.oshovskii.market.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final Cart cart;

    // нужна ли тут аннотация @Transactional ?
    public Order createFromUserCart(User user) {
        Order order = new Order(cart, user);
        order = orderRepository.save(order);
        cart.clear();
        return order;
    }

    public List<Order> findAllOrdersByOwnerName(String username) {
        return orderRepository.findAllByOwnerUsername(username);
    }
}

