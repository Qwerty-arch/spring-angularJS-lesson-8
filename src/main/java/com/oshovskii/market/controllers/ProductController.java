package com.oshovskii.market.controllers;

import com.oshovskii.market.model.Product;
import com.oshovskii.market.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> findAllProducts(
            @RequestParam(name = "min_price", defaultValue = "0") Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice
    ) {
        if (maxPrice == null) {
            maxPrice = Integer.MAX_VALUE;
        }
        return productService.findAllByPrice(minPrice, maxPrice);
    }

//    @GetMapping("/{id}")
//    public Product findProductById(@PathVariable Long id) {
//        return productService.findProductById(id).get();
//    }

    @PostMapping
    public Product saveNewProduct(@RequestBody Product product) {
        product.setId(null);
        return productService.saveOrUpdate(product);
    }

//    @DeleteMapping("/delete/{id}")
//    public void deleteById(@PathVariable Long id) {
//        productService.deleteById(id);
//    }

    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
