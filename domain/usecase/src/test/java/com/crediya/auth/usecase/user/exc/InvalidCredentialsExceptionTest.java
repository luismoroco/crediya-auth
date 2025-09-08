package com.crediya.auth.usecase.user.exc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidCredentialsExceptionTest {

  @Test
  void testConstructorWithMessageAndBody() {
    Map<String, Object> body = Map.of("field", "identityCardNumber");
    InvalidCredentialsException ex =
      new InvalidCredentialsException("Invalid credentials", body);

    assertEquals("Invalid credentials", ex.getMessage());
    assertEquals(body, ex.getBody());
  }

  @Test
  void testConstructorWithMessageOnly() {
    InvalidCredentialsException ex =
      new InvalidCredentialsException("Invalid user or password");

    assertEquals("Invalid user or password", ex.getMessage());
    assertEquals(ex.getBody(), new HashMap<>());
  }
}
