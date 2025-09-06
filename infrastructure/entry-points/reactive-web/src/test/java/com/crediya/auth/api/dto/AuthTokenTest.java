package com.crediya.auth.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTokenTest {

  @Test
  void testNoArgsConstructorAndSetters() {
    AuthToken token = new AuthToken();
    token.setToken("abc123");

    assertEquals("abc123", token.getToken());
  }

  @Test
  void testAllArgsConstructor() {
    AuthToken token = new AuthToken("xyz789");

    assertEquals("xyz789", token.getToken());
  }

  @Test
  void testBuilder() {
    AuthToken token = AuthToken.builder()
      .token("builderToken")
      .build();

    assertEquals("builderToken", token.getToken());
  }

  @Test
  void testToBuilder() {
    AuthToken token = AuthToken.builder().token("first").build();

    AuthToken modified = token.toBuilder().token("second").build();

    assertEquals("first", token.getToken());
    assertEquals("second", modified.getToken());
  }
}
