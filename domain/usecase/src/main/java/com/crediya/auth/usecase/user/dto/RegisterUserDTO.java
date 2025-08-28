package com.crediya.auth.usecase.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterUserDTO {

  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Long basicWaging;
  private String birthDate;
  private String address;

  public RegisterUserDTO() {
  }

  public RegisterUserDTO(String firstName, String lastName, String email, String identityCardNumber, String password,
                         String phoneNumber, Long basicWaging, String birthDate, String address) {
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

  @Override
  public String toString() {
    return String.format(
      "[firstName=%s][lastName=%s][email=%s][identityCardNumber=%s][password=%s][phoneNumber=%s][basicWaging=%s]" +
        "[birthDate=%s][address=%s]",
      firstName, lastName, email, identityCardNumber, password,
      phoneNumber, basicWaging, birthDate, address
    );
  }
}
