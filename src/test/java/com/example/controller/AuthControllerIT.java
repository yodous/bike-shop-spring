package com.example.controller;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.dto.UserView;
import com.example.exception.InvalidAddressEmailException;
import com.example.exception.InvalidPasswordException;
import com.example.exception.InvalidTokenException;
import com.example.exception.UsernameTakenException;
import com.example.exception.handler.GlobalExceptionHandler;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.JwtTokenFilter;
import com.example.security.JwtTokenService;
import com.example.service.AuthService;
import com.example.validation.RegisterValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(controllers = {AuthController.class, GlobalExceptionHandler.class})
class AuthControllerIT {
    private static final String LOGIN_PATH = "/api/auth/perform_login";
    private static final String REGISTER_PATH = "/api/auth/signup";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    @MockBean
    private JwtTokenService jwtTokenService;
    @MockBean
    private JwtTokenFilter jwtTokenFilter;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RegisterValidator registerValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_withValidCredentials_thenStatus200() throws Exception {
        LoginRequest loginRequest = new LoginRequest("test_user", "password".toCharArray());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("username", "password");
        given(authenticationManager.authenticate(any())).willReturn(authToken);
        given(authToken.getPrincipal()).willReturn(new Object());
        given(jwtTokenService.generateAccessToken(any())).willReturn("generated_token");
//        AuthenticationResponse authResponse = new AuthenticationResponse("token", new UserView("username", "fullName"));

//        given(authService.login(any())).willReturn(authResponse);

        mockMvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void givenRequestOnLoginMethod_WithUsernameNotExisting_Status401() throws Exception {
        LoginRequest loginRequest = new LoginRequest("test_user", "password".toCharArray());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), CharBuffer.wrap(loginRequest.getPassword()));

        doThrow(UsernameNotFoundException.class).when(authService).login(any());
        given(authenticationManager.authenticate(any())).willReturn(authToken);
        given(jwtTokenService.generateAccessToken(any())).willReturn("test_token");
        given(jwtTokenService.validate(anyString())).willReturn(false);

        MvcResult mvcResult = mockMvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void givenRegisterRequest_withValidCredentials_Status200() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest())))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentAsString()).contains("email").contains("sent");
    }

    @Test
    void givenRegisterRequest_withUsernameTaken_Status409() throws Exception {
        doThrow(UsernameTakenException.class).when(authService).register(any());

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");

        MvcResult mvcResult = mockMvc.perform(post(REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(409);
    }

    @Test
    void givenRegisterRequest_withPasswordNotMeetingRequirements_Status422() throws Exception {
        doThrow(InvalidPasswordException.class).when(authService).register(any());

        mockMvc.perform(post(REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest())))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void givenRegisterRequest_withEmailAlreadyTaken_Or_EmailNotMeetingRequirements_thenStatus422() throws Exception {
        doThrow(InvalidAddressEmailException.class).when(authService).register(any());

        mockMvc.perform(post(REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest())))
                .andExpect(status().isUnprocessableEntity());
    }

}