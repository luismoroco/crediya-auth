package com.crediya.auth.model.user.gateways;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class JwtProviderTest {

  private JwtProvider jwtProvider;

  @BeforeEach
  void setUp() {
    jwtProvider = mock(JwtProvider.class);
  }

  @Test
  void testGenerateToken() {
    User user = new User();
    user.setUserId(1L);
    user.setEmail("john@example.com");
    user.setUserRole(UserRole.CUSTOMER);

    String expectedToken = "mocked-jwt-token";

    when(jwtProvider.generate(user)).thenReturn(expectedToken);

    String actualToken = jwtProvider.generate(user);

    assertNotNull(actualToken);
    assertEquals(expectedToken, actualToken);

    verify(jwtProvider, times(1)).generate(user);
  }
}
