package com.crediya.auth.usecase.user.dto;

import com.crediya.auth.model.user.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {

  private UserRole userRole;
  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Integer basicWaging;

  public SignUpDTO() {
  }

  public SignUpDTO(UserRole userRole, String firstName, String lastName, String email, String identityCardNumber,
                   String password, String phoneNumber, Integer basicWaging) {
    this.userRole = userRole;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.identityCardNumber = identityCardNumber;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.basicWaging = basicWaging;
  }
}
