package com.example.validation;

import com.example.dto.SignupRequest;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupValidator {
    private final UserRepository userRepository;

    public void validate(SignupRequest signupRequest) {
        isUsernameTaken(signupRequest);
        PasswordValidator.isValid(signupRequest.getPassword(),
                signupRequest.getConfirmPassword());
        EmailValidator.isValid(signupRequest.getEmail());
    }

    private void isUsernameTaken(SignupRequest signupRequest) {
        userRepository.findByUsername(signupRequest.getUsername())
                .ifPresent(u -> {
                    throw new UsernameNotFoundException(
                            u.getUsername() + " is already taken.");
                });
    }
}
