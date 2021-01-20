package com.oshovskii.market.dto;

import com.oshovskii.market.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderProductDto {
    private Product product;
    private Integer quantity;

}
