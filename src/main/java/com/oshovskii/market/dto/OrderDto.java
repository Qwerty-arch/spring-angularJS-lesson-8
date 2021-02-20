package com.oshovskii.market.dto;

import com.oshovskii.market.model.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private String username;
    private String address;
    private int totalPrice;
    private String creationDateTime;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.username = order.getOwner().getUsername();
        this.address = order.getAddress();
        this.totalPrice = order.getPrice();
        this.creationDateTime = order.getCreatedAt().toString();
    }
}