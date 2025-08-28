package com.crediya.auth.model.user;

import lombok.Getter;

@Getter
public enum UserRole {
  ADMIN(1),
  USER(2);

  private final int code;

  UserRole(int code) {
    this.code = code;
  }

  public static UserRole fromCode(int code) {
    for (UserRole role : values()) {
      if (role.getCode() == code) {
        return role;
      }
    }

    throw new IllegalArgumentException("Invalid UserRole code: " + code);
  }
}
