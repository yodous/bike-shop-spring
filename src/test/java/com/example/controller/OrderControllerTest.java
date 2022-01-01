package com.example.controller;

import com.example.dto.OrderItemRequest;
import com.example.dto.OrderRequest;
import com.example.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private final String PATH = "/api/orders";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void getAllShouldFailWith401() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void orderSelectedFromCartShouldFailWith401() throws Exception {
        mockMvc.perform(post(PATH + "/cart"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void orderAllItemsFromCartShouldFailWith401() throws Exception {
        mockMvc.perform(post(PATH + "/cart/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getAllShouldSucceedWith200() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void orderSelectedFromCartShouldSucceedWith201() throws Exception {
        OrderRequest request=  new OrderRequest(List.of(
                        new OrderItemRequest(1, 1),
                        new OrderItemRequest(2, 3)),
                "TRANSFER");

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

}