package com.oshovskii.market.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class OrderProduct {

    @EmbeddedId
    @JsonIgnore
    private OrderProductPK orderProductPK;

    @Column(nullable = false)
    private Integer quantity;

    public OrderProduct(Order order, Product product, Integer quantity) {
        orderProductPK = new OrderProductPK();
        orderProductPK.setOrder(order);
        orderProductPK.setProduct(product);
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return this.orderProductPK.getProduct();
    }

    public OrderProductPK getOrderProductPK() {
        return orderProductPK;
    }

    public void setOrderProductPK(OrderProductPK orderProductPK) {
        this.orderProductPK = orderProductPK;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
