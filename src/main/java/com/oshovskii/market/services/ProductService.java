package com.oshovskii.market.services;

import com.oshovskii.market.dto.ProductDto;
import com.oshovskii.market.model.Product;
import com.oshovskii.market.repositories.ProductRepository;
import com.oshovskii.market.soap.products.ProductSoap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductDto> findProductDtoById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int pageSize) {
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(ProductDto::new);
    }

    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public static final Function<Product, ProductSoap> functionEntityToSoap = p -> {
        ProductSoap productSoap = new ProductSoap();
        productSoap.setId(p.getId());
        productSoap.setTitle(p.getTitle());
        productSoap.setPrice(p.getPrice());
        return productSoap;
    };

    public List<ProductSoap> getAllProducts() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }
}
