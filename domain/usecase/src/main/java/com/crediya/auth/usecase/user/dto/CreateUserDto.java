package com.crediya.auth.usecase.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
  private String name;
  private String description;
}
