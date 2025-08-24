package com.crediya.auth.r2dbc.entity;

import com.crediya.auth.model.user.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

  @Id
  private Long userId;
  @Enumerated(EnumType.ORDINAL) private UserType userType;
  private String firstName;
  private String lastName;
  private String email;
  private String identityCardNumber;
  private String password;
  private String phoneNumber;
  private Integer basicWaging;
}

