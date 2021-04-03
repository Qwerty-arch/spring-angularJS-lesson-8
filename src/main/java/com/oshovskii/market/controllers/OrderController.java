package com.oshovskii.market.controllers;

import com.oshovskii.market.dto.OrderDto;
import com.oshovskii.market.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.market.model.Order;
import com.oshovskii.market.services.CartService;
import com.oshovskii.market.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrderFromCart(Principal principal, @RequestParam UUID cartUuid, @RequestParam String address) {
        Order order = orderService.createFromUserCart(principal.getName(), cartUuid, address);
        cartService.clearCart(cartUuid);
        return new OrderDto(order);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new OrderDto(order);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findAllOrdersByOwnerName(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
