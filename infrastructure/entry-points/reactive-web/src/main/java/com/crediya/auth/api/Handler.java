package com.crediya.auth.api;

import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.logging.aspect.AutomaticLogging;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

  private final UserUseCase useCase;

  @AutomaticLogging
  public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(RegisterUserDTO.class)
      .flatMap(this.useCase::registerUser)
      .flatMap(dto -> ServerResponse
        .status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
      );
  }

  @AutomaticLogging
  public Mono<ServerResponse> getUserByEmail(ServerRequest serverRequest) {
    return this.useCase.getUserByEmail(serverRequest.pathVariable("email"))
      .flatMap(dto -> ServerResponse
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
      );
  }
}
