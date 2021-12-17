package com.example.controller;

import com.example.dto.CartRepresentation;
import com.example.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    private final String PATH = "/api/cart";
    private final String PATH_WITH_ID = "/api/cart/{id}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    @WithMockUser
    void getShouldReturnCartDto() throws Exception {
        given(cartService.get()).willReturn(new CartRepresentation(null, 0));
        mockMvc.perform(get(PATH))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void addProductShouldSucceedWith204() throws Exception {
        mockMvc.perform(post(PATH)
                        .param("id", "1")
                        .param("quantity", "1"))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser
    void deleteProductShouldReturnCartDto() throws Exception {
        mockMvc.perform(delete(PATH_WITH_ID, 1)
                        .param("quantity", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteAllProductsShouldReturnCartDto() throws Exception {
        mockMvc.perform(delete(PATH + "/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getShouldFailWith401() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addProductShouldFailWith401() throws Exception {
        mockMvc.perform(post(PATH_WITH_ID, 1)
                        .param("quantity", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteProductShouldFailWith401() throws Exception {
        mockMvc.perform(delete(PATH_WITH_ID, 1)
                        .param("quantity", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteAllProductsShouldFailWith401() throws Exception {
        mockMvc.perform(delete(PATH + "/all"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}