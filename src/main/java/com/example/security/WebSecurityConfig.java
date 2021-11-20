package com.example.security;

import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final JwtTokenFilter jwtTokenFilter;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(r -> r
                        .antMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterAfter(
                        jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, ex) -> {
                            String authHeader = request.getHeader("Authorization");

                            if (authHeader == null || authHeader.isEmpty()) {
                                log.error("Unauthenticated request");
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                            }
                        }))
                .formLogin(l -> l
                        .loginPage("/login.html")
                        .defaultSuccessUrl("/secured")
                        .loginProcessingUrl("/perform_login")
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login")))
                .logout(l -> l
                        .logoutUrl("/perform_logout")
                        .addLogoutHandler(new HeaderWriterLogoutHandler(
                                new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL))
                        )
                        .logoutSuccessUrl("/"))
                .headers(h -> h
                        .frameOptions().disable());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                        username -> userRepository.findByUsername(username).orElseThrow(
                                () -> new UsernameNotFoundException(
                                        String.format("User: %s, not found", username)))
                )
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}