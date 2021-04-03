package com.oshovskii.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price_per_product")
    private int pricePerProduct;

    @Column(name = "price")
    private int price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.pricePerProduct = product.getPrice();
        this.price = this.pricePerProduct;
    }

    public void incrementQuantity() {
        quantity++;
        price = quantity * pricePerProduct;
    }

    public void incrementQuantity(int amount) {
        quantity += amount;
        price = quantity * pricePerProduct;
    }

    public void decrementQuantity() {
        quantity--;
        price = quantity * pricePerProduct;
    }
}

