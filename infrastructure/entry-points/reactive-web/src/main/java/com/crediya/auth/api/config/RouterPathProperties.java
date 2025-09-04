package com.crediya.auth.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "route.path")
public class RouterPathProperties {

  private AuthPath auth;
  private UserPath user;


  @Data
  public static class AuthPath {
    private String login;
  }

  @Data
  public static class UserPath {
    private String registerUser;
    private String getUserByIdentityCardNumber;
    private String getUsers;
  }
}
