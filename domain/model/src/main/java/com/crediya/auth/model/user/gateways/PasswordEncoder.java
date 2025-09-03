package com.crediya.auth.model.user.gateways;

public interface PasswordEncoder {

  String encode(String password);

  boolean matches(String rawPassword, String encodedPassword);
}
