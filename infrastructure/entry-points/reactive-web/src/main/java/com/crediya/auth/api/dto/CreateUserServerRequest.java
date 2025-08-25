package com.crediya.auth.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserServerRequest(
  @NotBlank(message = "Name cannot be blank") String firstName,
  String lastName,
  String email,
  String identityCardNumber,
  String password,
  String phoneNumber,
  Integer basicWaging
) {
}
