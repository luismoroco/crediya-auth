package com.crediya.auth.model.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  @Test
  void testNoArgsConstructor() {
    User user = new User();
    assertNotNull(user);
    assertNull(user.getUserId());
    assertNull(user.getFirstName());
    assertNull(user.getLastName());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDate birthDate = LocalDate.of(2000, 1, 1);
    User user = new User(
      1L,
      1,
      "John",
      "Doe",
      "john@example.com",
      "123456",
      "password",
      "999999999",
      1000L,
      birthDate,
      "Street 123"
    );

    assertEquals(1L, user.getUserId());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals("john@example.com", user.getEmail());
    assertEquals("123456", user.getIdentityCardNumber());
    assertEquals("password", user.getPassword());
    assertEquals("999999999", user.getPhoneNumber());
    assertEquals(1000L, user.getBasicWaging());
    assertEquals(1, user.getUserRoleId());
    assertEquals(birthDate, user.getBirthDate());
    assertEquals("Street 123", user.getAddress());
  }

  @Test
  void testUserRoleSetterAndGetter() {
    User user = new User();
    user.setUserRole(UserRole.CUSTOMER);

    assertEquals(UserRole.CUSTOMER.getCode(), user.getUserRoleId());
    assertEquals(UserRole.CUSTOMER, user.getUserRole());
  }

  @Test
  void testFieldEnumLabels() {
    assertEquals("USER ROLE", User.Field.USER_ROLE.toString());
    assertEquals("FIRST NAME", User.Field.FIRST_NAME.toString());
    assertEquals("LAST NAME", User.Field.LAST_NAME.toString());
    assertEquals("EMAIL", User.Field.EMAIL.toString());
    assertEquals("IDENTITY CARD NUMBER", User.Field.IDENTITY_CARD_NUMBER.toString());
    assertEquals("PASSWORD", User.Field.PASSWORD.toString());
    assertEquals("PHONE NUMBER", User.Field.PHONE_NUMBER.toString());
    assertEquals("BASIC WAGING", User.Field.BASIC_WAGING.toString());
    assertEquals("BIRTH DATE", User.Field.BIRTH_DATE.toString());
    assertEquals("ADDRESS", User.Field.ADDRESS.toString());
  }

  @Test
  void testUserRoleFromInvalidCode() {
    User user = new User();
    user.setUserRoleId(999);

    assertThrows(IllegalArgumentException.class, user::getUserRole);
  }
}
