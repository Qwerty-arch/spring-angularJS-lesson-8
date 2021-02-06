package com.oshovskii.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "orderItem_id")
    private Long orderItemId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quantity")
    private int quantity;

    public Order(Long orderItemId, Long userId, int quantity) {
        this.orderItemId = orderItemId;
        this.userId = userId;
        this.quantity = quantity;
    }
}
