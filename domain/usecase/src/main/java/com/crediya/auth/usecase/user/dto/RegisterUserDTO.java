package com.crediya.auth.usecase.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
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

  @Override
  public String toString() {
    return String.format(
      "[firstName=%s][lastName=%s][email=%s][identityCardNumber=%s][password=%s][phoneNumber=%s][basicWaging=%s]" +
        "[birthDate=%s][address=%s]",
      this.firstName, this.lastName, this.email, this.identityCardNumber, this.password,
      this.phoneNumber, this.basicWaging, this.birthDate, this.address
    );
  }
}
