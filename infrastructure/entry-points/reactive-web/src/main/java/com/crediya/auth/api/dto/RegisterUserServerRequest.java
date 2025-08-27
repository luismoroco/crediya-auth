package com.crediya.auth.api.dto;

import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.mapping.Mappable;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterUserServerRequest implements Mappable<RegisterUserDTO> {

  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Long basicWaging;
  private LocalDate birthDate;
  private String address;

  @Override
  public Class<RegisterUserDTO> getTargetClass() {
    return RegisterUserDTO.class;
  }
}
