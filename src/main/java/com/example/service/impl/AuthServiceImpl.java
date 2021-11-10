package com.example.service.impl;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.SignupRequest;
import com.example.exception.TokenNotFoundException;
import com.example.mapper.UserViewMapper;
import com.example.model.*;
import com.example.repository.AccountActivationTokenRepository;
import com.example.repository.UserRepository;
import com.example.security.JwtTokenUtil;
import com.example.service.AuthService;
import com.example.service.EmailService;
import com.example.validation.SignupValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AccountActivationTokenRepository authTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final SignupValidator signupValidator;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${token.expiration}")
    private int tokenExpiration;

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        signupValidator.validate(signupRequest);

        User user = mapSignupRequestToUser(signupRequest);
        userRepository.save(user);

        ActivationEmail activationEmail = new ActivationEmail(
                user.getEmail(), generateActivationToken(user));
        emailService.sendMessage(activationEmail);

        log.info("email has been sent\n" + activationEmail.getText());
    }

    private  User mapSignupRequestToUser(SignupRequest signupRequest) {
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

    private String generateActivationToken(User user) {
        String token = UUID.randomUUID().toString();
        AccountActivationToken accountActivationToken = new AccountActivationToken(
                user, token, Instant.now().plus(tokenExpiration, ChronoUnit.MILLIS));
        authTokenRepository.save(accountActivationToken);

        return token;
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(), loginRequest.getPassword()
                        )
                );

        User principal = (User) authenticate.getPrincipal();
        String token = jwtTokenUtil.generateAccessToken(principal);

        return new AuthenticationResponse(token, UserViewMapper.mapToView(principal));
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

}
