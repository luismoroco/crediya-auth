package com.crediya.auth.api;

import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.auth.usecase.user.dto.GetUsersDTO;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.logging.aspect.AutomaticLogging;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserHandler {

  private final UserUseCase useCase;

  @AutomaticLogging
  @PreAuthorize("hasRole('ADVISOR')")
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
  public Mono<ServerResponse> getUserByIdentityCardNumber(ServerRequest serverRequest) {
    return this.useCase.getUserByIdentityCardNumber(serverRequest.pathVariable("identity_card_number"))
      .flatMap(dto -> ServerResponse
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
      );
  }

  @AutomaticLogging
  public Mono<ServerResponse> getUsers(ServerRequest serverRequest) {
    MultiValueMap<String, String> queryParams = serverRequest.queryParams();

    GetUsersDTO request = GetUsersDTO.builder()
      .identityCardNumbers(queryParams.getOrDefault("identity_card_numbers", List.of()))
      .build();

    return this.useCase.getUsers(request)
      .collectList()
      .flatMap(dto -> ServerResponse
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
      );
  }
}
