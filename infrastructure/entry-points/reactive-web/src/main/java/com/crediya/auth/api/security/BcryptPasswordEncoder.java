package com.crediya.auth.api.security;

import com.crediya.auth.model.user.gateways.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BcryptPasswordEncoder implements PasswordEncoder {

  private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

  @Override
  public String encode(String password) {
    return this.passwordEncoder.encode(password);
  }

  @Override
  public boolean matches(String rawPassword, String encodedPassword) {
    return this.passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
