package com.crediya.auth.model.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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

  public User() {
  }

  public User(Long userId, String firstName, String lastName, String email, String identityCardNumber, String password,
              String phoneNumber, Long basicWaging, Integer userRoleId, LocalDate birthDate,  String address) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.identityCardNumber = identityCardNumber;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.basicWaging = basicWaging;
    this.userRoleId = userRoleId;
    this.birthDate = birthDate;
    this.address = address;
  }

  public void setUserRole(UserRole userRole) {
    this.userRoleId = userRole.getCode();
  }

  public UserRole getUserRole() {
    return UserRole.fromCode(this.userRoleId);
  }
}
