package com.crediya.auth.model.user;

import lombok.Getter;
import lombok.Setter;

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
  private Integer basicWaging;

  public User() {
  }

  public User(Long userId, String firstName, String lastName, String email, String identityCardNumber, String password,
              String phoneNumber, Integer basicWaging, Integer userRoleId) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.identityCardNumber = identityCardNumber;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.basicWaging = basicWaging;
    this.userRoleId = userRoleId;
  }

}
