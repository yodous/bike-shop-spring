package com.example.controller;

import com.example.dto.CartItemRepresentation;
import com.example.dto.CartRepresentation;
import com.example.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    private final String PATH = "/api/cart";
    private final String PATH_WITH_ID = "/api/cart/{id}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @Test
    @WithMockUser
    void get_WithRoleUser_ShouldReturnCartDto() throws Exception {
        given(cartService.get()).willReturn(new CartRepresentation(null, 0));
        mockMvc.perform(get(PATH))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getCartItem_shouldSucceedWith200() throws Exception {
        CartItemRepresentation cartItemRepresentation = new CartItemRepresentation(
                1, "imgurl", "trek madone", 5000, 3, 1500);
        given(cartService.getItemByProductId(1)).willReturn(cartItemRepresentation);

        mockMvc.perform(get(PATH_WITH_ID, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(cartItemRepresentation)));
    }

    @Test
    @WithMockUser
    void addCartItem_withRoleUser_shouldSucceedWith204() throws Exception {
        mockMvc.perform(post(PATH_WITH_ID , 1))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser
    void deleteCartItem_WithRoleUser_ShouldReturnCartDto() throws Exception {
        mockMvc.perform(delete(PATH_WITH_ID, 1)
                        .param("quantity", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteAllCartItems_WithRoleUser_ShouldReturnCartDto() throws Exception {
        mockMvc.perform(delete(PATH + "/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    void get_Unauthenticated_ShouldFailWith401() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addCartItem_Unauthenticated_ShouldFailWith401() throws Exception {
        mockMvc.perform(post(PATH_WITH_ID, 1)
                        .param("quantity", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteCartItem_Unauthenticated_ShouldFailWith401() throws Exception {
        mockMvc.perform(delete(PATH_WITH_ID, 1)
                        .param("quantity", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteAllCartItems_Unauthenticated_FailWith401() throws Exception {
        mockMvc.perform(delete(PATH + "/all"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}