package com.example.service.impl;

import com.example.dto.RegisterRequest;
import com.example.dto.RegisterResponse;
import com.example.exception.InvalidAddressEmailException;
import com.example.model.Address;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.AuthService;
import com.example.validation.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;

    @Value(value = "regex.email")
    private String emailRegex;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        isUsernameTaken(registerRequest);
        PasswordValidator.isValid(registerRequest.getPassword());
        PasswordValidator.arePasswordsTheSame(
                registerRequest.getPassword(), registerRequest.getConfirmPassword());
        emailValidate(registerRequest);

        User user = new User(registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                new Address(registerRequest.getCity(),
                        registerRequest.getStreet(),
                        registerRequest.getPostalCode()));

        repository.save(user);
        return null;
    }

    private void emailValidate(RegisterRequest registerRequest) {
        if (!registerRequest.getEmail().matches(emailRegex))
            throw new InvalidAddressEmailException();
    }

    private void isUsernameTaken(RegisterRequest registerRequest) {
        repository.findByUsername(registerRequest.getUsername())
                .ifPresent(u -> {throw new UsernameNotFoundException(
                            registerRequest.getUsername() + " is already taken.");
                });
    }
}
