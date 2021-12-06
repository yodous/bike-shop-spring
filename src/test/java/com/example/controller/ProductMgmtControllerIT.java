package com.example.controller;

import com.example.dto.ProductRequest;
import com.example.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductMgmtControllerIT {
    private static final String PATH = "/api/products";
    private static final String PATH_WITH_ID = "/api/products/{id}";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void unauthorizedPostRequest_thenStatus401() throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unauthorizedPutRequest_thenStatus401() throws Exception {
        int productId = 1;

        mockMvc.perform(put(PATH_WITH_ID, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unauthorizedPatchRequest_thenStatus401() throws Exception {
        int productId = 1;
        mockMvc.perform(patch(PATH_WITH_ID, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unauthorizedDeleteRequest_thenStatus401() throws Exception {
        int productId = 1;

        mockMvc.perform(delete(PATH_WITH_ID, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void authorizedCreateRequest_thenStatus201() throws Exception {
        ProductRequest productRequest = new ProductRequest();

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void authorizedUpdateRequest_thenStatus204() throws Exception {
        int productId = 1;
        ProductRequest productRequest = new ProductRequest();

        mockMvc.perform(put(PATH_WITH_ID, productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void authorizedUpdatePriceRequest_thenStatus204() throws Exception {
        int productId = 1;
        double newPrice = 9.99;

        mockMvc.perform(patch(PATH_WITH_ID, productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPrice)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void authorizedDeleteRequest_thenStatus204() throws Exception {
        int productId = 1;

        mockMvc.perform(delete(PATH_WITH_ID, productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}