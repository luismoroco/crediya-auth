package com.crediya.auth.usecase.user.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetUsersDTOTest {

  @Test
  void testBuilderAndGetters() {
    List<String> ids = Arrays.asList("123", "456", "789");
    GetUsersDTO dto = GetUsersDTO.builder()
      .identityCardNumbers(ids)
      .build();

    assertEquals(ids, dto.getIdentityCardNumbers());
  }

  @Test
  void testSettersAndGetters() {
    GetUsersDTO dto = new GetUsersDTO();
    dto.setIdentityCardNumbers(Collections.singletonList("999"));

    assertEquals(Collections.singletonList("999"), dto.getIdentityCardNumbers());
  }

  @Test
  void testAllArgsConstructor() {
    List<String> ids = Arrays.asList("111", "222");
    GetUsersDTO dto = new GetUsersDTO(ids);

    assertEquals(ids, dto.getIdentityCardNumbers());
  }

  @Test
  void testNoArgsConstructor() {
    GetUsersDTO dto = new GetUsersDTO();
    assertNull(dto.getIdentityCardNumbers());
  }

  @Test
  void testToString() {
    List<String> ids = Arrays.asList("101", "202");
    GetUsersDTO dto = new GetUsersDTO(ids);

    String expected = "[identityCardNumbers=[101, 202]]";
    assertEquals(expected, dto.toString());
  }
}
