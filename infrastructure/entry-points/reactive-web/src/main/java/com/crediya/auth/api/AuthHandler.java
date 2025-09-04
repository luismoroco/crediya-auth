package com.crediya.auth.api;

import com.crediya.auth.api.dto.AuthToken;
import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.auth.usecase.user.dto.LogInDTO;
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
public class AuthHandler {

  private final UserUseCase useCase;

  @AutomaticLogging
  public Mono<ServerResponse> logIn(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(LogInDTO.class)
      .flatMap(this.useCase::logIn)
      .flatMap(dto -> ServerResponse
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(
          AuthToken.builder()
          .token(dto)
          .build()
        )
      );
  }
}
