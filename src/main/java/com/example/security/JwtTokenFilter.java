package com.example.security;

import com.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.security.RolesAllowed;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@RolesAllowed({})
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final String BEARER_HEADER = "Bearer ";

    public JwtTokenFilter(JwtTokenService jwtTokenService,
                          UserRepository userRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_HEADER)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.replace(BEARER_HEADER, "").trim();
        if (!jwtTokenService.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userRepository
                .findByUsername(jwtTokenService.getUsername(token))
                .orElseThrow();

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);

        log.info("AUTHORIZATION TOKEN = " + token);
    }
}
