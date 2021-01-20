package com.oshovskii.market.repositories;

import com.oshovskii.market.model.OrderProduct;
import com.oshovskii.market.model.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}
