package com.example.service.impl;

import com.example.dto.OrderDetailsResponse;
import com.example.dto.OrderItemRequest;
import com.example.dto.OrderRequest;
import com.example.model.User;
import com.example.model.enums.PaymentType;
import com.example.repository.UserRepository;
import com.example.security.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
// TODO ML: why do You need DirtiesContext? It's usually (not always) an indicator, that Your tests are poor designed; this will cause Your whole context to be loaded before every method, causing Your class to last longer than it should
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "/db/populateDb.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OrderServiceImplIT {

	private final String PATH = "/api/orders";
	/**
	 * ID of the user from /db/populateDb.sql
	 */
	private static final int USER_ID = 1;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
	@Autowired
	private JwtTokenService jwtTokenService;
	@Autowired
	private UserRepository userRepository;

	// TODO ML: use the less-required-visibility;
    @Value("${jwt}")
    String token;

	// TODO ML: use the less-required-visibility;
	// TODO ML: You are using this in only 2 tests; why You set this up for all tests then?
    OrderRequest orderRequest;

    @BeforeEach
    public void setup() {
        OrderItemRequest orderItemRequest1 = new OrderItemRequest(1, 1);
        OrderItemRequest orderItemRequest2 = new OrderItemRequest(2, 2);
        List<OrderItemRequest> orderItems = List.of(orderItemRequest1, orderItemRequest2);
        orderRequest = new OrderRequest(orderItems,
                "fullname", "email", "city", "street",
                "postalCode", PaymentType.TRANSFER.getValue());
    }

    @Test
    void getAll_shouldFail_status401() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isUnauthorized());
    }

	/**
	 * Just an example of {@link WithMockUser}
	 **/
	@WithMockUser(roles = "ADMIN")
    @Test
    void getAll_shouldSucceed_status200_withMockUser() throws Exception {
        String responseJson = mockMvc.perform(get(PATH)
                        .header(HttpHeaders.AUTHORIZATION, token) //todo: fails because jwt has expired
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseJson).isNotEmpty();

        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

        assertThat(orderDetailsResponse.getOrderDetails()).hasSize(1);
        assertThat(orderDetailsResponse.getOrderDetails().get(0).getItems()).hasSize(2);
    }

    @Test
    void getAll_shouldSucceed_status200() throws Exception {
		// TODO ML: fetch or create user as a part of 'given' part
		final User user = userRepository.findById(USER_ID).get();
		// TODO ML: generate a valid token as a preparation for Your test or mock/spy on JwtTokenFilter and mock the validation
		final String token = jwtTokenService.generateAccessToken(user);

		String responseJson = mockMvc.perform(get(PATH)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseJson).isNotEmpty(); // TODO ML: test is failing because the response is empty

        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

        assertThat(orderDetailsResponse.getOrderDetails()).hasSize(1);
        assertThat(orderDetailsResponse.getOrderDetails().get(0).getItems()).hasSize(2);
    }

    @Test
    void orderSelectedFromCart_shouldFail_status401() throws Exception {
        mockMvc.perform(get(PATH)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void orderSelectedFromCart_shouldSucceed_status201() throws Exception {
        mockMvc.perform(post(PATH)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}
