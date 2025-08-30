package com.crediya.auth.model.user;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

  private Long userId;
  private Integer userRoleId;
  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Long basicWaging;
  private LocalDate birthDate;
  private String address;

  public void setUserRole(UserRole userRole) {
    this.userRoleId = userRole.getCode();
  }

  public UserRole getUserRole() {
    return UserRole.fromCode(this.userRoleId);
  }

  public enum Field {

    USER_ROLE,
    FIRST_NAME,
    LAST_NAME,
    EMAIL,
    IDENTITY_CARD_NUMBER,
    PASSWORD,
    PHONE_NUMBER,
    BASIC_WAGING,
    BIRTH_DATE,
    ADDRESS;

    @Override
    public String toString() {
      return this.name().replaceAll("_", " ");
    }
  }
}
