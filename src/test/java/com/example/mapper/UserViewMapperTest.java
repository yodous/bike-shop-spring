package com.example.mapper;

import com.example.dto.RegisterRequest;
import com.example.dto.UserView;
import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserViewMapperTest {

    @Autowired
    private UserViewMapper mapper;

    @Test
    void testMapRegisterRequestToUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        String rawPassword = "password";
        registerRequest.setPassword(rawPassword.toCharArray());

        User actual = mapper.mapRegisterRequestToUser(registerRequest);

        String hashedPassword = actual.getPassword();
        assertThat(hashedPassword).isNotEqualTo(rawPassword);
    }

    @Test
    void testMapToView() {
        User user = new User();
        user.setUsername("bob_ross");
        user.setFirstName("Bob");
        user.setLastName("Ross");

        UserView actual = mapper.mapToView(user);

        assertThat(actual.getUsername()).isEqualTo("bob_ross");
        assertThat(actual.getFullName()).isEqualTo("Bob Ross");
    }
}