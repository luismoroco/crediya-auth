package com.crediya.auth.api.dto;

import com.crediya.auth.usecase.user.dto.CreateUserDto;
import com.crediya.common.mapping.Mappable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest implements Mappable<CreateUserDto> {
  private String name;
  private String description;

  @Override
  public Class<CreateUserDto> getTargetClass() {
    return CreateUserDto.class;
  }
}
