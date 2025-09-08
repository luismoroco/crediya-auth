package com.crediya.auth.api.security;

import com.crediya.auth.api.exc.InvalidTokenException;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtAuthenticationManagerTest {

  private JwtProvider jwtProvider;
  private JwtAuthenticationManager manager;

  @BeforeEach
  void setUp() {
    jwtProvider = mock(JwtProvider.class);
    manager = new JwtAuthenticationManager(jwtProvider);
  }

  @Test
  void shouldAuthenticateValidToken() {
    Authentication authentication = new UsernamePasswordAuthenticationToken("ignored", "validToken");

    Claims claims = mock(Claims.class);
    when(jwtProvider.getClaims("validToken")).thenReturn(claims);
    when(claims.get("role", String.class)).thenReturn("USER");
    when(claims.getSubject()).thenReturn("john_doe");

    Authentication result = manager.authenticate(authentication).block();

    assertNotNull(result);
    assertEquals("john_doe", result.getPrincipal());
    assertTrue(result instanceof UsernamePasswordAuthenticationToken);
    assertTrue(result.getAuthorities().stream()
      .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

    verify(jwtProvider, times(1)).getClaims("validToken");
  }

  @Test
  void shouldThrowInvalidTokenExceptionOnError() {
    Authentication authentication = new UsernamePasswordAuthenticationToken("ignored", "badToken");

    when(jwtProvider.getClaims("badToken")).thenThrow(new RuntimeException("JWT parsing error"));

    InvalidTokenException ex = assertThrows(
      InvalidTokenException.class,
      () -> manager.authenticate(authentication).block()
    );

    assertEquals("Invalid token formation", ex.getMessage());
  }
}
