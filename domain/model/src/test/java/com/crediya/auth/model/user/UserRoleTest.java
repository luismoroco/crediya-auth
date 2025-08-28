package com.crediya.auth.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

  @Test
  void testGetCode() {
    assertEquals(1, UserRole.ADMIN.getCode());
    assertEquals(2, UserRole.USER.getCode());
  }

  @Test
  void testFromCodeValid() {
    assertEquals(UserRole.ADMIN, UserRole.fromCode(1));
    assertEquals(UserRole.USER, UserRole.fromCode(2));
  }

  @Test
  void testFromCodeInvalid() {
    int invalidCode = 999;
    IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> UserRole.fromCode(invalidCode)
    );

    assertTrue(exception.getMessage().contains("Invalid UserRole code: " + invalidCode));
  }
}
