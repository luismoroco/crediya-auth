package com.crediya.auth.usecase.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LogInDTO {

  private String email;
  private String password;

  @Override
  public String toString() {
    return String.format("[email=%s][password=%s]", this.email, this.password);
  }
}
