package com.crediya.auth.usecase.user;

import com.crediya.auth.usecase.user.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

  public Mono<String> createUser(CreateUserDto dto) {
    return Mono.just(dto.getDescription());
  }
}
