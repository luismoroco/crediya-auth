package com.crediya.auth.api;

import com.crediya.auth.api.dto.CreateUserServerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

  private final ValidationService validationService;

  public Mono<ServerResponse> listenPOSTSignUpUseCase(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateUserServerRequest.class)
      .flatMap(validationService::validate)
      .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED).bodyValue(dto));
  }
}
