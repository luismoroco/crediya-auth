package com.crediya.auth.api.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RouterPathPropertiesTest {

  @Test
  void shouldSetAndGetAuthPath() {
    RouterPathProperties.AuthPath authPath = new RouterPathProperties.AuthPath();
    authPath.setLogin("/login");

    assertThat(authPath.getLogin()).isEqualTo("/login");
  }

  @Test
  void shouldSetAndGetUserPath() {
    RouterPathProperties.UserPath userPath = new RouterPathProperties.UserPath();
    userPath.setRegisterUser("/register");
    userPath.setGetUserByIdentityCardNumber("/users/{id}");
    userPath.setGetUsers("/users");

    assertThat(userPath.getRegisterUser()).isEqualTo("/register");
    assertThat(userPath.getGetUserByIdentityCardNumber()).isEqualTo("/users/{id}");
    assertThat(userPath.getGetUsers()).isEqualTo("/users");
  }
}
