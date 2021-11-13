package com.example.validation;

import com.example.dto.RegisterRequest;
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
        isUsernameTaken(registerRequest);
        passwordValidator.isValid(registerRequest.getPassword(),
                registerRequest.getConfirmPassword());
        emailValidator.isValid(registerRequest.getEmail());
    }

    private void isUsernameTaken(RegisterRequest registerRequest) {
        userRepository.findByUsername(registerRequest.getUsername())
                .ifPresent(u -> {
                    throw new UsernameTakenException();
                });
    }
}
