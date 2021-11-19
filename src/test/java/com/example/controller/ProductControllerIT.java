package com.example.controller;

import com.example.dto.ProductView;
import com.example.exception.ProductNotFoundException;
import com.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
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
    private final String PRODUCT_PATH = "/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductView productView;
    private ProductView productView1;

    @BeforeEach
    void setup() {
        productView = new ProductView(
                "product name 0", "ELECTRONICS", 65, Instant.now());
        productView1 = new ProductView(
                "product name 1", "SPORT", 123, Instant.now());
    }

    @Test
    void getByString_thenReturnListOf2DTOsWithStatusIsOk() throws Exception {
        given(productService.getByString(anyString())).willReturn(mockedData());
        mockMvc.perform(get(PRODUCT_PATH + "?name=keyboard"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("product name 0"))
                .andExpect(jsonPath("$[1].name").value("product name 1"));

    }

    private List<ProductView> mockedData() {
        return List.of(productView, productView1);
    }

    @Test
    void getByString_thenReturnEmptyListWithStatusIsOk() throws Exception {
        given(productService.getByString(anyString())).willReturn(Collections.emptyList());

        mockMvc.perform(get(PRODUCT_PATH + "?name=giraffe"))
                .andExpect(content().string("[]"))
                .andExpect(status().isOk());
    }

    @Test
    void get_withValidId_thenStatusIsOk() throws Exception {
        given(productService.get(anyInt())).willReturn(productView);

        mockMvc.perform(get(PRODUCT_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("product name 0"))
                .andExpect(jsonPath("$.price").value(65));
    }

    @Test
    void get_withInvalidId_thenStatus404() throws Exception {
        String expectedBody = "Could not find product with id=1";
        given(productService.get(anyInt())).willThrow(new ProductNotFoundException(1));

        mockMvc.perform(get(PRODUCT_PATH + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedBody));

    }

    @Test
    void getByCategory_thenStatusIsOK() throws Exception {
        given(productService.getByCategory("electronics")).willReturn(mockedData());

        mockMvc.perform(get(PRODUCT_PATH + "/categories/electronics"))
                .andExpect(status().isOk());
    }

    @Test
    void getByUsername_thenStatusIsOK() throws Exception {
        given(productService.getAllByUsername(anyString())).willReturn(mockedData());
        mockMvc.perform(get(PRODUCT_PATH + "/users/test_username"))
                .andExpect(status().isOk());
    }

}