package com.crediya.auth.api.exc;

import com.crediya.common.exc.BadRequestException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvalidTokenExceptionTest {

  @Test
  void shouldCreateWithMessageAndBody() {
    String message = "Token inválido";
    Map<String, Object> body = Map.of("error", "expired");

    InvalidTokenException ex = new InvalidTokenException(message, body);

    assertEquals(message, ex.getMessage());
    assertEquals(body, ex.getBody());
    assertTrue(ex instanceof BadRequestException);
  }

  @Test
  void shouldCreateWithMessageOnly() {
    String message = "Token corrupto";

    InvalidTokenException ex = new InvalidTokenException(message);

    assertEquals(message, ex.getMessage());
    assertTrue(ex instanceof BadRequestException);
  }
}
