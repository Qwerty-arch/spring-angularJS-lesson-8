package com.oshovskii.market.dto;

import com.oshovskii.market.model.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private String username;
    private int totalPrice;
    private String creationDateTime;
    private String userAddress;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.username = order.getOwner().getUsername();
        this.userAddress = order.getOwner().getAddress();
        this.totalPrice = order.getPrice();
        this.creationDateTime = order.getCreatedAt().toString();
    }
}