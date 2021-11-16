package com.example.controller;

import com.example.dto.ProductView;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.enums.ProductCategory;
import com.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {
    private static final String PRODUCT_PATH = "/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final Product product = new Product("product name",
            "product description", ProductCategory.ELECTRONICS, 65, new User());
    private final ProductView productView = new ProductView(
            "product name", "ELECTRONICS", 65, Instant.now());

    @Test
    void whenGetByString_thenStatusIsOk() throws Exception {
        given(productService.getByString(anyString())).willReturn(List.of(productView));
        mockMvc.perform(get(PRODUCT_PATH + "?string=keyboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("product name"))
                .andExpect(jsonPath("$[0].category").value("ELECTRONICS"))
                .andExpect(jsonPath("$[0].price").value(65));
    }

    @Test
    void whenGetById_thenStatusIsOK() throws Exception {
        given(productService.get(anyInt())).willReturn(productView);

        mockMvc.perform(get(PRODUCT_PATH + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetByCategory_thenStatusIsOK() throws Exception {
        given(productService.getByCategory(anyString())).willReturn(List.of(productView));

        mockMvc.perform(get(PRODUCT_PATH + "/categories/electronics"))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetByUsername_thenStatusIsOK() throws Exception {
        given(productService.getAllByUsername(anyString())).willReturn(List.of(productView));
        mockMvc.perform(get(PRODUCT_PATH + "/users/test_username"))
                .andExpect(status().isOk());
    }

}