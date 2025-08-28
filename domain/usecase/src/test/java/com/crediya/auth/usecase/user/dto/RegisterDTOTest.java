package com.crediya.auth.usecase.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterDTOTest {

  @Test
  void testNoArgsConstructorAndSetters() {
    RegisterUserDTO dto = new RegisterUserDTO();
    dto.setFirstName("John");
    dto.setLastName("Doe");
    dto.setEmail("john@example.com");
    dto.setIdentityCardNumber("12345678");
    dto.setPassword("pass123");
    dto.setPhoneNumber("999999999");
    dto.setBasicWaging(1500L);
    dto.setBirthDate("1990-01-01");
    dto.setAddress("Street 123");

    assertEquals("John", dto.getFirstName());
    assertEquals("Doe", dto.getLastName());
    assertEquals("john@example.com", dto.getEmail());
    assertEquals("12345678", dto.getIdentityCardNumber());
    assertEquals("pass123", dto.getPassword());
    assertEquals("999999999", dto.getPhoneNumber());
    assertEquals(1500L, dto.getBasicWaging());
    assertEquals("1990-01-01", dto.getBirthDate());
    assertEquals("Street 123", dto.getAddress());
  }

  @Test
  void testBuilder() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("Jane")
      .lastName("Smith")
      .email("jane@example.com")
      .identityCardNumber("87654321")
      .password("password")
      .phoneNumber("888888888")
      .basicWaging(2000L)
      .birthDate("1985-12-31")
      .address("Avenue 456")
      .build();

    assertEquals("Jane", dto.getFirstName());
    assertEquals("Smith", dto.getLastName());
    assertEquals("jane@example.com", dto.getEmail());
    assertEquals("87654321", dto.getIdentityCardNumber());
    assertEquals("password", dto.getPassword());
    assertEquals("888888888", dto.getPhoneNumber());
    assertEquals(2000L, dto.getBasicWaging());
    assertEquals("1985-12-31", dto.getBirthDate());
    assertEquals("Avenue 456", dto.getAddress());
  }
}
