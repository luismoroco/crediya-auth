package com.crediya.auth.api.security;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtProviderTest {

  private JwtProvider jwtProvider;

  @BeforeEach
  void setUp() {
    jwtProvider = new JwtProvider();

    String secret = "c2VjcmV0MTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTI=";
    ReflectionTestUtils.setField(jwtProvider, "secret", secret);

    ReflectionTestUtils.setField(jwtProvider, "expiration", 3600000);
  }

  @Test
  void shouldGenerateAndParseToken() {
    User user = new User();
    user.setUserId(1L);
    user.setEmail("john.doe@example.com");
    user.setUserRole(UserRole.CUSTOMER);
    user.setIdentityCardNumber("ABC123456");

    String token = jwtProvider.generate(user);
    assertNotNull(token);

    Claims claims = jwtProvider.getClaims(token);

    assertEquals(user.getEmail(), claims.getSubject());
    assertEquals(user.getUserRole().name(), claims.get("role"));
    assertEquals(user.getIdentityCardNumber(), claims.get("identityCardNumber"));
    assertNotNull(claims.getExpiration());
  }

  @Test
  void shouldFailForInvalidToken() {
    String badToken = "invalid.token.value";

    assertThrows(Exception.class, () -> jwtProvider.getClaims(badToken));
  }
}
