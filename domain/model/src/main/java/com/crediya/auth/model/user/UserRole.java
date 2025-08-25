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
}
