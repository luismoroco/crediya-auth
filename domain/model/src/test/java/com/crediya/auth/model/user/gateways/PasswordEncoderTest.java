package com.crediya.auth.model.user.gateways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PasswordEncoderTest {

  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    passwordEncoder = mock(PasswordEncoder.class);
  }

  @Test
  void testEncodePassword() {
    String rawPassword = "mySecret123";
    String encodedPassword = "encoded-secret";

    when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

    String result = passwordEncoder.encode(rawPassword);

    assertNotNull(result);
    assertEquals(encodedPassword, result);

    verify(passwordEncoder, times(1)).encode(rawPassword);
  }

  @Test
  void testPasswordMatches() {
    String rawPassword = "mySecret123";
    String encodedPassword = "encoded-secret";

    when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

    boolean result = passwordEncoder.matches(rawPassword, encodedPassword);

    assertTrue(result);

    verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
  }
}
