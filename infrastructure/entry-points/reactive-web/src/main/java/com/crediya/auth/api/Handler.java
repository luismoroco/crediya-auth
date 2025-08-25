package com.crediya.auth.api;

import com.crediya.auth.api.dto.SignUpServerRequest;
import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.common.mapping.Mappable;
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

  private final UserUseCase useCase;

  public Mono<ServerResponse> listenPOSTSignUp(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(SignUpServerRequest.class)
      .flatMap(ObjectValidator.get()::validate)
      .map(Mappable::map)
      .flatMap(this.useCase::signUp)
      .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED).bodyValue(dto));
  }
}
