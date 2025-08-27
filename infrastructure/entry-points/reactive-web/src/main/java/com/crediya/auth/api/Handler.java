package com.crediya.auth.api;

import com.crediya.auth.api.dto.RegisterUserServerRequest;
import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.common.mapping.Mappable;
import com.crediya.common.validation.ObjectValidator;

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

  public Mono<ServerResponse> listenPOSTRegisterUser(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(RegisterUserServerRequest.class)
      .flatMap(ObjectValidator.get()::validate)
      .map(Mappable::map)
      .flatMap(this.useCase::registerUser)
      .flatMap(dto -> ServerResponse
        .status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
      );
  }

  public Mono<ServerResponse> listenPOSTGetUserByEmail(ServerRequest serverRequest) {
    return this.useCase.getUserByEmail(serverRequest.pathVariable("email"))
      .flatMap(dto -> ServerResponse
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
      );
  }
}
