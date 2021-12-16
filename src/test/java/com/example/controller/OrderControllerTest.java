package com.example.controller;

import com.example.dto.OrderOneItemRequest;
import com.example.model.enums.PaymentType;
import com.example.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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

    private String paymentType;

    @BeforeEach
    void setup() {
        paymentType = PaymentType.TRANSFER.getValue();
    }

    @Test
    void getAllShouldFailWith401() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void orderOneShouldFailWith401() throws Exception {
        OrderOneItemRequest request = new OrderOneItemRequest(1, 1, paymentType);
        mockMvc.perform(post(PATH)
                        .content(objectMapper.writeValueAsString(request)))
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
    void orderOneShouldSucceedWith201() throws Exception {
        OrderOneItemRequest request = new OrderOneItemRequest(1, 1, paymentType);
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void orderSelectedFromCartShouldSucceedWith201() throws Exception {
        mockMvc.perform(post(PATH + "/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2,3]"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void orderAllItemsFromCartShouldSucceedWith201() throws Exception {
        mockMvc.perform(post(PATH + "/cart/all"))
                .andExpect(status().isCreated());
    }

}