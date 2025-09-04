package com.crediya.auth.usecase.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterUserDTOTest {

  @Test
  void testAllArgsConstructorAndGetters() {
    RegisterUserDTO dto = new RegisterUserDTO(
      "John",
      "Doe",
      "john.doe@example.com",
      "12345678",
      "securePass",
      "987654321",
      1500L,
      "1999-01-01",
      "123 Main St"
    );

    assertEquals("John", dto.getFirstName());
    assertEquals("Doe", dto.getLastName());
    assertEquals("john.doe@example.com", dto.getEmail());
    assertEquals("12345678", dto.getIdentityCardNumber());
    assertEquals("securePass", dto.getPassword());
    assertEquals("987654321", dto.getPhoneNumber());
    assertEquals(1500L, dto.getBasicWaging());
    assertEquals("1999-01-01", dto.getBirthDate());
    assertEquals("123 Main St", dto.getAddress());
  }

  @Test
  void testBuilderAndToBuilder() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("Jane")
      .lastName("Smith")
      .email("jane.smith@example.com")
      .identityCardNumber("87654321")
      .password("pass123")
      .phoneNumber("123456789")
      .basicWaging(2000L)
      .birthDate("2000-12-31")
      .address("456 Another St")
      .build();

    assertEquals("Jane", dto.getFirstName());
    assertEquals("Smith", dto.getLastName());

    RegisterUserDTO modified = dto.toBuilder().firstName("Alice").build();
    assertEquals("Alice", modified.getFirstName());
    assertEquals("Smith", modified.getLastName());
  }

  @Test
  void testNoArgsConstructorAndSetters() {
    RegisterUserDTO dto = new RegisterUserDTO();
    dto.setFirstName("Mike");
    dto.setLastName("Jordan");
    dto.setEmail("mike.jordan@example.com");

    assertEquals("Mike", dto.getFirstName());
    assertEquals("Jordan", dto.getLastName());
    assertEquals("mike.jordan@example.com", dto.getEmail());
  }

  @Test
  void testToString() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("Anna")
      .lastName("Taylor")
      .email("anna.taylor@example.com")
      .identityCardNumber("99887766")
      .password("secret")
      .phoneNumber("1122334455")
      .basicWaging(3000L)
      .birthDate("1995-05-05")
      .address("789 Broadway")
      .build();

    String str = dto.toString();

    assertTrue(str.contains("firstName=Anna"));
    assertTrue(str.contains("lastName=Taylor"));
    assertTrue(str.contains("email=anna.taylor@example.com"));
    assertTrue(str.contains("identityCardNumber=99887766"));
    assertTrue(str.contains("password=secret"));
    assertTrue(str.contains("phoneNumber=1122334455"));
    assertTrue(str.contains("basicWaging=3000"));
    assertTrue(str.contains("birthDate=1995-05-05"));
    assertTrue(str.contains("address=789 Broadway"));
  }
}
