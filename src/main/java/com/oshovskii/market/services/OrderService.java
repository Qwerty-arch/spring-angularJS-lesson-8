package com.oshovskii.market.services;

import com.oshovskii.market.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.market.model.Order;
import com.oshovskii.market.model.OrderItem;
import com.oshovskii.market.model.User;
import com.oshovskii.market.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;

//    public List<Order> findAllOrders() {
//        return orderRepository.findAll();
//    }
//
//    public Order findOrderById(Long id) throws ResourceNotFoundException {
//        return orderRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Заказ не найден!")
//        );
//    }

    public Order saveOrder(OrderItem orderItem, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Order order = new Order(orderItem.getId(),user.getId(), orderItem.getQuantity());
        return orderRepository.save(order);
    }


}