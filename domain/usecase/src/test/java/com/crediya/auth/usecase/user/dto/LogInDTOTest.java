package com.crediya.auth.usecase.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LogInDTOTest {

  @Test
  void testBuilderAndGetters() {
    LogInDTO dto = LogInDTO.builder()
      .email("test@email.com")
      .password("secret123")
      .build();

    assertEquals("test@email.com", dto.getEmail());
    assertEquals("secret123", dto.getPassword());
  }

  @Test
  void testSettersAndGetters() {
    LogInDTO dto = new LogInDTO();
    dto.setEmail("john.doe@mail.com");
    dto.setPassword("mypassword");

    assertEquals("john.doe@mail.com", dto.getEmail());
    assertEquals("mypassword", dto.getPassword());
  }

  @Test
  void testAllArgsConstructor() {
    LogInDTO dto = new LogInDTO("jane@mail.com", "pwd123");

    assertEquals("jane@mail.com", dto.getEmail());
    assertEquals("pwd123", dto.getPassword());
  }

  @Test
  void testNoArgsConstructor() {
    LogInDTO dto = new LogInDTO();
    assertNull(dto.getEmail());
    assertNull(dto.getPassword());
  }

  @Test
  void testToString() {
    LogInDTO dto = new LogInDTO("user@mail.com", "securePwd");

    String expected = "[email=user@mail.com][password=securePwd]";
    assertEquals(expected, dto.toString());
  }
}
