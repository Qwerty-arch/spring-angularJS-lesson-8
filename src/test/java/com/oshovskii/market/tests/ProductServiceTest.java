package com.oshovskii.market.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.oshovskii.market.model.Product;
import com.oshovskii.market.repositories.ProductRepository;
import com.oshovskii.market.services.ProductService;

import java.util.Optional;

@SpringBootTest(classes = ProductService.class)
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testGetProduct() {
        Product demoProduct = new Product();
        demoProduct.setTitle("KitKat");
        demoProduct.setPrice(50);
        demoProduct.setId(10L);

        Mockito
                .doReturn(Optional.of(demoProduct))
                .when(productRepository)
                .findById(10L);

        Product p = productService.findProductById(10L).get();

        Mockito.verify(productRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(10L));
        Assertions.assertEquals("KitKat", p.getTitle());
    }

    @Test
    public void testDeleteById() {
        Product demoProduct = new Product();
        demoProduct.setTitle("KitKat");
        demoProduct.setPrice(50);
        demoProduct.setId(10L);

        Mockito
                .doNothing()
                .when(productRepository)
                .deleteById(10L);


        Mockito.verify(productRepository, Mockito.times(1)).deleteById(ArgumentMatchers.eq(10L));
    }

    @Test
    public void testSaveProduct() {
        Product demo = new Product();
        demo.setId(10L);
        demo.setTitle("KitKat");
        demo.setPrice(100);

        Mockito
                .doReturn(Optional.of(demo))
                .when(productRepository)
                .save(demo);

        Assertions.assertEquals("KitKat", demo.getTitle());
    }
}
