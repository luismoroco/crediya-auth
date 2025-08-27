package com.crediya.auth.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Long basicWaging;
  private String address;

  //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate birthDate;
}
