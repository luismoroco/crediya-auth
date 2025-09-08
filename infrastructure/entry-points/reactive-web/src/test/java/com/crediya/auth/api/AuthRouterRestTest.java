package com.crediya.auth.api;

import com.crediya.auth.api.config.RouterPathProperties;
import com.crediya.auth.api.dto.AuthToken;
import com.crediya.auth.model.user.User;
import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.auth.usecase.user.dto.LogInDTO;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.api.handling.GlobalExceptionFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthRouterRestTest {

  private UserUseCase useCase;
  private WebTestClient webTestClient;
  private RouterPathProperties routerPathProperties;
  private RouterPathProperties.AuthPath authPath;

  @BeforeEach
  void setUp() {
    routerPathProperties = new RouterPathProperties();
    authPath = new RouterPathProperties.AuthPath();
    authPath.setLogin("/api/v1/auth/log-in");
    routerPathProperties.setAuth(authPath);

    useCase = mock(UserUseCase.class);
    AuthHandler authHandler = new AuthHandler(useCase);
    RouterFunction<?> routes = new AuthRouterRest(authHandler, new GlobalExceptionFilter(), routerPathProperties)
      .authRouterFunction();
    webTestClient = WebTestClient.bindToRouterFunction(routes)
      .build();
  }

  @Test
  void mustRegisterUser() {
    LogInDTO dto = new LogInDTO();
    dto.setEmail("root@gmail.com");
    dto.setPassword("root");

    AuthToken authToken = new AuthToken();
    authToken.setToken("token123");

    when(useCase.logIn(org.mockito.ArgumentMatchers.any(LogInDTO.class)))
      .thenReturn(Mono.just("token123"));

    webTestClient.post()
      .uri(authPath.getLogin())
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dto)
      .exchange()
      .expectStatus().isOk();
  }
}
