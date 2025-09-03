package com.crediya.auth.usecase.user.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GetUsersDTO {

  private List<String> identityCardNumbers;

  @Override
  public String toString() {
    return String.format("[identityCardNumbers=%s]", identityCardNumbers);
  }
}
