package com.crediya.auth.api;

import com.crediya.auth.api.dto.CreateUserServerRequest;
import com.crediya.common.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

  public Mono<ServerResponse> listenPOSTSignUpUseCase(ServerRequest serverRequest) {

    return serverRequest.bodyToMono(CreateUserServerRequest.class)
      .flatMap(ObjectValidator.get()::validate)
      .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED).bodyValue(dto));
  }
}
