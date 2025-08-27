package com.crediya.auth.usecase.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterUserDTO {

  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Long basicWaging;
  private LocalDate birthDate;
  private String address;

  public RegisterUserDTO() {
  }

  public RegisterUserDTO(String firstName, String lastName, String email, String identityCardNumber, String password,
                         String phoneNumber, Long basicWaging, LocalDate birthDate, String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.identityCardNumber = identityCardNumber;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.basicWaging = basicWaging;
    this.birthDate = birthDate;
    this.address = address;
  }
}
