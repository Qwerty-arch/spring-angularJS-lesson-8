package com.oshovskii.market.tests;

import com.oshovskii.market.model.User;
import com.oshovskii.market.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.oshovskii.market.model.Product;
import com.oshovskii.market.repositories.ProductRepository;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoriesTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void initDbProductsTest() {
        List<Product> genresList = productRepository.findAll();
        Assertions.assertEquals(20, genresList.size());
    }

    @Test
    public void initDbUsersTest() {
        List<User> genresList = userRepository.findAll();
        Assertions.assertEquals(2, genresList.size());
    }
}
