package com.oshovskii.market.services;

import com.oshovskii.market.model.Product;
import com.oshovskii.market.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAllByPrice(int min, int max) {
        return productRepository.findAllByPriceBetween(min, max);
    }

    public void deleteProductById(Long id) { productRepository.deleteById(id);}

    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }
}
