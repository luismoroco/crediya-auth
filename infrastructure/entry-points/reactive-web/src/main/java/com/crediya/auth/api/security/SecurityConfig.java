package com.crediya.auth.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String[] PUBLIC_URLS = { "/api/v1/auth/log-in", "/api/v1/auth/sign-up" };

  private final SecurityContextRepository securityContextRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JwtFilter jwtFilter) {
    return http
      .csrf(ServerHttpSecurity.CsrfSpec::disable)
      .authorizeExchange(exchange -> exchange
        .pathMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
        .anyExchange().permitAll()
      )
      .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
      .securityContextRepository(securityContextRepository)
      .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
      .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
      .logout(ServerHttpSecurity.LogoutSpec::disable)
      .build();
  }
}
