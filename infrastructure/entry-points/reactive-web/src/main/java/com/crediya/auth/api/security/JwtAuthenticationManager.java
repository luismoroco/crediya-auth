package com.crediya.auth.api.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtProvider jwtProvider;

  public JwtAuthenticationManager(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Mono.just(authentication)
      .map(auth -> jwtProvider.getClaims(auth.getCredentials().toString()))
      .onErrorResume(e -> Mono.error(new Throwable("bad token")))
      .map(claims -> {
        String role = claims.get("role", String.class);
        List<GrantedAuthority> authorities =
          List.of(new SimpleGrantedAuthority("ROLE_" + role));

        return new UsernamePasswordAuthenticationToken(
          claims.getSubject(),
          null,
          authorities
        );
      });
  }
}
