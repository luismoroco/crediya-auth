package com.crediya.auth.r2dbc.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {

  @Test
  void testAllArgsConstructorAndGetters() {
    LocalDate birthDate = LocalDate.of(1990, 1, 1);

    UserEntity user = new UserEntity(
      1L,
      2,
      "John",
      "Doe",
      "john@example.com",
      "12345678",
      "pass123",
      "999999999",
      1000L,
      birthDate,
      "Street 123"
    );

    assertThat(user.getUserId()).isEqualTo(1L);
    assertThat(user.getUserRoleId()).isEqualTo(2);
    assertThat(user.getFirstName()).isEqualTo("John");
    assertThat(user.getLastName()).isEqualTo("Doe");
    assertThat(user.getEmail()).isEqualTo("john@example.com");
    assertThat(user.getIdentityCardNumber()).isEqualTo("12345678");
    assertThat(user.getPassword()).isEqualTo("pass123");
    assertThat(user.getPhoneNumber()).isEqualTo("999999999");
    assertThat(user.getBasicWaging()).isEqualTo(1000L);
    assertThat(user.getBirthDate()).isEqualTo(birthDate);
    assertThat(user.getAddress()).isEqualTo("Street 123");
  }

  @Test
  void testNoArgsConstructorAndSetters() {
    UserEntity user = new UserEntity();
    LocalDate birthDate = LocalDate.of(1990, 1, 1);

    user.setUserId(1L);
    user.setUserRoleId(2);
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setEmail("john@example.com");
    user.setIdentityCardNumber("12345678");
    user.setPassword("pass123");
    user.setPhoneNumber("999999999");
    user.setBasicWaging(1000L);
    user.setBirthDate(birthDate);
    user.setAddress("Street 123");

    assertThat(user.getUserId()).isEqualTo(1L);
    assertThat(user.getUserRoleId()).isEqualTo(2);
    assertThat(user.getFirstName()).isEqualTo("John");
    assertThat(user.getLastName()).isEqualTo("Doe");
    assertThat(user.getEmail()).isEqualTo("john@example.com");
    assertThat(user.getIdentityCardNumber()).isEqualTo("12345678");
    assertThat(user.getPassword()).isEqualTo("pass123");
    assertThat(user.getPhoneNumber()).isEqualTo("999999999");
    assertThat(user.getBasicWaging()).isEqualTo(1000L);
    assertThat(user.getBirthDate()).isEqualTo(birthDate);
    assertThat(user.getAddress()).isEqualTo("Street 123");
  }

  @Test
  void testBuilder() {
    LocalDate birthDate = LocalDate.of(1990, 1, 1);

    UserEntity user = UserEntity.builder()
      .userId(1L)
      .userRoleId(2)
      .firstName("John")
      .lastName("Doe")
      .email("john@example.com")
      .identityCardNumber("12345678")
      .password("pass123")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .birthDate(birthDate)
      .address("Street 123")
      .build();

    assertThat(user.getFirstName()).isEqualTo("John");
    assertThat(user.getUserRoleId()).isEqualTo(2);
    assertThat(user.getBirthDate()).isEqualTo(birthDate);
    assertThat(user.getAddress()).isEqualTo("Street 123");
  }
}
