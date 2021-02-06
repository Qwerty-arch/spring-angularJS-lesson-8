package com.oshovskii.market.controllers;

import com.oshovskii.market.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.market.model.Order;
import com.oshovskii.market.model.OrderItem;
import com.oshovskii.market.model.User;
import com.oshovskii.market.services.OrderService;
import com.oshovskii.market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

//    @GetMapping()
//    public List<Order> findAllOrders() {
//        return orderService.findAllOrders();
//    }
//
//    @GetMapping("/{id}")
//    public Order findOrderById(@PathVariable Long id) throws ResourceNotFoundException {
//        return orderService.findOrderById(id);
//    }

    @GetMapping("/add")
    public Order saveOrder(@RequestBody OrderItem orderItem, Principal principal) {
        return orderService.saveOrder(orderItem, principal);
    }
}
