package com.oshovskii.market.tests;

import com.oshovskii.market.model.Product;
import com.oshovskii.market.repositories.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.oshovskii.market.dto.ProductDto;
import com.oshovskii.market.services.ProductService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductsControllerTest {
    @Autowired
    private MockMvc mvc;

    private MultiValueMap<String, String> params() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "");
        params.add("min_price", "");
        params.add("max_price", "");
        params.add("page", "0");
        params.add("count", "2");
        return params;
    }

    @Test
    @Order(1)
    public void getAllProductsNoParamsTest() throws Exception {
        mvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].title", is("Bread")));
    }

    @Test
    @Order(2)
    public void getAllProductsInPageCountTestTest() throws Exception {
        MultiValueMap<String, String> mvm = params();
        for (int i = 0; i < 2; i++) {
            mvm.set("count", String.valueOf(i));
            mvc.perform(get("/api/v1/products")
                    .params(mvm)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content", hasSize(Integer.parseInt(mvm.getFirst("count")))))
                    .andExpect(jsonPath("$.content[0].title", is("Bread")));
        }
    }

    @Test
    public void getAllProductsTest() throws Exception {
        mvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].title", is("Bread")))
                .andExpect(jsonPath("$.content[0].price", greaterThan(10)));
    }


}

