package com.oshovskii.market.repositories;

import com.oshovskii.market.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
