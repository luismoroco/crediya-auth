package com.crediya.auth.api.dto;

import com.crediya.auth.model.user.UserRole;
import com.crediya.auth.usecase.user.dto.SignUpDTO;
import com.crediya.common.mapping.Mappable;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpServerRequest implements Mappable<SignUpDTO> {

  @NotNull private UserRole userRole;
  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @NotBlank @Email private String email;
  @NotBlank private String identityCardNumber;
  @NotBlank private String password;
  @NotBlank private String phoneNumber;
  @NotNull @Min(0) @Max(15000000) private Integer basicWaging;

  @Override
  public Class<SignUpDTO> getTargetClass() {
    return SignUpDTO.class;
  }
}
