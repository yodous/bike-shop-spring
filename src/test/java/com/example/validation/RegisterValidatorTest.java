package com.example.validation;

import com.example.dto.RegisterRequest;
import com.example.exception.InvalidAddressEmailException;
import com.example.exception.InvalidPasswordException;
import com.example.exception.UsernameTakenException;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterValidatorTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordValidator passwordValidator;
    @Mock
    private EmailValidator emailValidator;
    @InjectMocks
    private RegisterValidator validator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private final RegisterRequest request = new RegisterRequest();

    @Test
    void registerWithValidRequest_ThenOk() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validate(request),
                "Should not throw any exception, because request is valid");
        verify(passwordValidator).isValid(request.getPassword(), request.getConfirmPassword());
        verify(emailValidator).isValid(request.getEmail());
    }

    @Test
    void registerWithUsernameTaken_ThenThrowUsernameTakenException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(UsernameTakenException.class,
                () -> validator.validate(request),
                "Should throw UsernameTakenException, because username is taken");
        assertThat(exception).isInstanceOf(UsernameTakenException.class);
        verify(passwordValidator, never()).isValid(request.getPassword(), request.getConfirmPassword());
        verify(emailValidator, never()).isValid(request.getEmail());
    }

    @Test
    void registerWithInvalidPassword_ThenThrowInvalidPasswordException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        doThrow(InvalidPasswordException.class).when(passwordValidator).isValid(any(), any());

        Exception exception = assertThrows(InvalidPasswordException.class,
                () -> validator.validate(request),
                "Should throw InvalidPasswordException, because password is not valid");
        assertThat(exception).isInstanceOf(InvalidPasswordException.class);
        verify(passwordValidator).isValid(request.getPassword(), request.getConfirmPassword());
        verify(emailValidator, never()).isValid(request.getEmail());
    }

    @Test
    void invalidAddressEmail_ThenThrowInvalidAddressEmailException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        doThrow(InvalidAddressEmailException.class).when(emailValidator).isValid(any());

        Exception exception = assertThrows(InvalidAddressEmailException.class,
                () -> validator.validate(request),
                "Should throw InvalidAddressEmailException, " +
                        "because address email is not valid");
        assertThat(exception).isInstanceOf(InvalidAddressEmailException.class);
        verify(passwordValidator).isValid(request.getPassword(), request.getConfirmPassword());
        verify(emailValidator).isValid(request.getEmail());
    }
}