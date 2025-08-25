package com.crediya.auth.usecase.user.dto;

import com.crediya.auth.model.user.UserRol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
  private UserRol userRol;
  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Integer basicWaging;
}
