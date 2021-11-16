package com.example.controller;

import com.example.dto.ProductCreateRequest;
import com.example.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
    private static final String PRODUCT_MGMT_PATH = "/products";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void unauthorizedPostRequest_thenStatus401() throws Exception {
        mockMvc.perform(post(PRODUCT_MGMT_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unauthorizedPutRequest_thenStatus401() throws Exception {
        int productId = 1;

        mockMvc.perform(put(PRODUCT_MGMT_PATH + "/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unauthorizedPatchRequest_thenStatus401() throws Exception {
        int productId = 1;
        mockMvc.perform(patch(PRODUCT_MGMT_PATH + "/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unauthorizedDeleteRequest_thenStatus401() throws Exception {
        int productId = 1;

        mockMvc.perform(delete(PRODUCT_MGMT_PATH + "/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void authorizedCreateRequest_thenStatus201() throws Exception {
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();

        mockMvc.perform(post(PRODUCT_MGMT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void authorizedUpdateRequest_thenStatus204() throws Exception {
        int productId = 1;
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();

        mockMvc.perform(put(PRODUCT_MGMT_PATH + "/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void authorizedUpdatePriceRequest_thenStatus204() throws Exception {
        int productId = 1;
        double newPrice = 9.99;

        mockMvc.perform(patch(PRODUCT_MGMT_PATH + "/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPrice)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void authorizedDeleteRequest_thenStatus204() throws Exception {
        int productId = 1;

        mockMvc.perform(delete(PRODUCT_MGMT_PATH + "/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}