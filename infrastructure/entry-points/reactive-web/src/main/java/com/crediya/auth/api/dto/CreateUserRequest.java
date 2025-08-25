package com.crediya.auth.api.dto;

import com.crediya.auth.model.user.UserRol;
import com.crediya.auth.usecase.user.dto.CreateUserDto;
import com.crediya.common.mapping.Mappable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest implements Mappable<CreateUserDto> {
  private UserRol userRol;
  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Integer basicWaging;

  @Override
  public Class<CreateUserDto> getTargetClass() {
    return CreateUserDto.class;
  }
}
