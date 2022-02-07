package com.example.security;

import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String BEARER_HEADER = "Bearer ";
    private final List<String> excludedUrls = Arrays.asList("/auth/perform_login",
            "/auth/perform_signup", "/auth/account-verification", "/products?name=**",
            "/products/**", "/categories");
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (jwtTokenService.validate(token)) {
            UserDetails userDetails = userRepository
                    .findByUsername(jwtTokenService.getUsername(token))
                    .orElseThrow();

            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
		// TODO ML: for a performance perspective, I would prepare the collection of AntPathRequestMatcher and re-use it (so it won't be re-created every time)
        return excludedUrls.stream().anyMatch(
                url -> new AntPathRequestMatcher(url).matches(request));
    }

	// TODO ML: missing javadoc - in some special case it will return empty string instead of real token
	// TODO ML: since this is a special case, that sometimes it returns empty string, I would suggest to distinguish situation when there is no bearer token and when the bearer token is realy an empty string; maybe Optional?
    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		// TODO ML: redudant assignment - simple return
        String token = "";

        if (header != null && header.startsWith(BEARER_HEADER))
            token = header.replace(BEARER_HEADER, "").trim();
        return token;
    }
}
