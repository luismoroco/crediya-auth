package com.crediya.auth.api.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BcryptPasswordEncoderTest {

  private org.springframework.security.crypto.password.PasswordEncoder delegate;
  private BcryptPasswordEncoder encoder;

  @BeforeEach
  void setUp() {
    delegate = mock(PasswordEncoder.class);
    encoder = new BcryptPasswordEncoder(delegate);
  }

  @Test
  void shouldDelegateEncode() {
    String password = "secret";
    String encoded = "$bcrypt$encoded";

    when(delegate.encode(password)).thenReturn(encoded);

    String result = encoder.encode(password);

    assertEquals(encoded, result);
    verify(delegate, times(1)).encode(password);
  }

  @Test
  void shouldDelegateMatches() {
    String raw = "secret";
    String encoded = "$bcrypt$encoded";

    when(delegate.matches(raw, encoded)).thenReturn(true);

    boolean result = encoder.matches(raw, encoded);

    assertTrue(result);
    verify(delegate, times(1)).matches(raw, encoded);
  }
}
