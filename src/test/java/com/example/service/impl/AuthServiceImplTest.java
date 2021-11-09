package com.example.service.impl;

import com.example.repository.AccountActivationTokenRepository;
import com.example.repository.UserRepository;
import com.example.service.EmailService;
import com.example.validation.SignupValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountActivationTokenRepository authTokenRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SignupValidator signupValidator;
    @InjectMocks
    AuthServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup_WhenSignupRequestIsValid_ThenOk() {
//        Address testAddress = new Address("city", "street", "12-345");
//        User user = new User("user", "password", "firstname",
//                "lastname", "test@email.io", "1234 5678 8765 4321", new UserRole(Role.USER), testAddress);
//        SignupRequest signupRequest = new SignupRequest(
//                "user", "password", "password", "firstname", "lastname",
//                "test@email.io", "1234 5678 8765 4321", "city", "street", "12-345", "USER");
//        when(userRepository.save(any())).thenReturn(user);
//
//        service.signup(signupRequest);
//
//        verify(signupValidator).validate(signupRequest);
//        verify(emailService).sendMessage(any());
    }

}