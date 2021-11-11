package com.example.mapper;

import com.example.dto.SignupRequest;
import com.example.dto.UserView;
import com.example.model.Address;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserViewMapper {
    private final PasswordEncoder passwordEncoder;

    public UserView mapToView(User user) {
        return new UserView(user.getId(), user.getUsername(), user.getFirstName() + user.getLastName());
    }

    public User mapSignupRequestToUser(SignupRequest signupRequest) {
        return new User(signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                signupRequest.getAccNumber(),
                new Address(signupRequest.getCity(),
                        signupRequest.getStreet(),
                        signupRequest.getPostalCode()),
                signupRequest.getRole());
    }
}