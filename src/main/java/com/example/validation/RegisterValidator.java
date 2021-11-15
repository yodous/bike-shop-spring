package com.example.validation;

import com.example.dto.RegisterRequest;
import com.example.exception.InvalidAddressEmailException;
import com.example.exception.UsernameTakenException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterValidator {
    private final UserRepository userRepository;
    private final PasswordValidator passwordValidator;
    private final EmailValidator emailValidator;

    public void validate(RegisterRequest registerRequest) {
        isUsernameTaken(registerRequest.getUsername());
        passwordValidator.isValid(registerRequest.getPassword(),
                registerRequest.getConfirmPassword());
        isAddressEmailTaken(registerRequest.getEmail());
        emailValidator.isValid(registerRequest.getEmail());
    }

    private void isUsernameTaken(String username) {
        userRepository.findByUsername(username)
                .ifPresent(u -> {
                    throw new UsernameTakenException();
                });
    }

    private void isAddressEmailTaken(String email) {
        userRepository.findByEmail(email)
                .ifPresent(u -> {
                    throw new InvalidAddressEmailException();
                });
    }
}
