package com.example.service.impl;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.SignupRequest;
import com.example.exception.InvalidPasswordException;
import com.example.exception.TokenNotFoundException;
import com.example.model.ActivationEmail;
import com.example.model.Address;
import com.example.model.AccountActivationToken;
import com.example.model.User;
import com.example.repository.AccountActivationTokenRepository;
import com.example.repository.UserRepository;
import com.example.service.AuthService;
import com.example.service.EmailService;
import com.example.validation.SignupValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AccountActivationTokenRepository authTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final SignupValidator signupValidator;

    @Value("${token.expiration}")
    private int tokenExpiration;

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        signupValidator.validate(signupRequest);

        User user = new User(signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                signupRequest.getAccNumber(),
                new Address(signupRequest.getCity(),
                        signupRequest.getStreet(),
                        signupRequest.getPostalCode()));
        userRepository.save(user);
        ActivationEmail activationEmail = new ActivationEmail(
                user.getEmail(), generateActivationToken(user));

        emailService.sendMessage(activationEmail);
        log.info("email has been sent\n" + activationEmail.getText());
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {

        return null;
    }

    @Override
    @Transactional
    public void enableUser(String token) {
        AccountActivationToken authToken = authTokenRepository.findAll().stream()
                .filter(t -> t.getToken().equals(token)).findAny()
                .orElseThrow(TokenNotFoundException::new);
        tokenExpired(authToken);

        User user = authToken.getUser();
        if (user == null)
            throw new UsernameNotFoundException("Could not find user");

        user.setEnabled(true);
        authToken.setModifiedAt(Instant.now());

        userRepository.save(user);
        authTokenRepository.save(authToken);
        log.info("user has been enabled");
    }

    private void tokenExpired(AccountActivationToken authToken) {
        long expirationDate = authToken.getCreatedAt().toEpochMilli() + tokenExpiration;
        long currentDate = Instant.now().toEpochMilli();

        if (expirationDate - currentDate < 0) {
            authTokenRepository.delete(authToken);
            userRepository.delete(authToken.getUser());

            throw new TokenNotFoundException("Token expired");
        }
    }

    private String generateActivationToken(User user) {
        String token = UUID.randomUUID().toString();
        AccountActivationToken accountActivationToken = new AccountActivationToken(
                user, token, Instant.now().plus(tokenExpiration, ChronoUnit.MILLIS));
        authTokenRepository.save(accountActivationToken);

        return token;
    }
}
