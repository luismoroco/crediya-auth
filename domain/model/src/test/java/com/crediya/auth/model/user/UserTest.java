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
      "John",
      "Doe",
      "john@example.com",
      "123456",
      "password",
      "999999999",
      1000L,
      1,
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
    user.setUserRole(UserRole.USER);

    assertEquals(UserRole.USER.getCode(), user.getUserRoleId());
    assertEquals(UserRole.USER, user.getUserRole());
  }

  @Test
  void testFieldEnumLabels() {
    assertEquals("User Role", User.Field.USER_ROLE.getLabel());
    assertEquals("First Name", User.Field.FIRST_NAME.getLabel());
    assertEquals("Last Name", User.Field.LAST_NAME.getLabel());
    assertEquals("Email", User.Field.EMAIL.getLabel());
    assertEquals("Identity Card Number", User.Field.IDENTITY_CARD_NUMBER.getLabel());
    assertEquals("Password", User.Field.PASSWORD.getLabel());
    assertEquals("Phone Number", User.Field.PHONE_NUMBER.getLabel());
    assertEquals("Basic Waging", User.Field.BASIC_WAGING.getLabel());
    assertEquals("Birth Date", User.Field.BIRTH_DATE.getLabel());
    assertEquals("Address", User.Field.ADDRESS.getLabel());
  }

  @Test
  void testUserRoleFromInvalidCode() {
    User user = new User();
    user.setUserRoleId(999);

    assertThrows(IllegalArgumentException.class, user::getUserRole);
  }
}
