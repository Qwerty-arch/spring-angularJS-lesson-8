package com.oshovskii.market.utils;

import com.oshovskii.market.dto.ProductDto;
import com.oshovskii.market.model.Product;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    /*
     * Из entity(product) в DTO
     */
    public ProductDto mapToProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        return dto;
    }

    /*
     * Из DTO в entity(product)
     */
    public Product mapToProductEntity(ProductDto dto) {
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}
