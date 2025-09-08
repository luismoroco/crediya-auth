package com.crediya.auth.api;

import com.crediya.auth.api.dto.AuthToken;
import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.auth.usecase.user.dto.LogInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthHandlerTest {

  @InjectMocks
  private AuthHandler handler;

  @Mock
  private UserUseCase useCase;

  @Mock
  private ServerRequest serverRequest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLoginSuccess() {
    LogInDTO dto = new LogInDTO();
    dto.setEmail("root@gmail.com");
    dto.setPassword("root");

    AuthToken authToken = new AuthToken();
    authToken.setToken("token123");

    when(serverRequest.bodyToMono(LogInDTO.class)).thenReturn(Mono.just(dto));
    when(useCase.logIn(any(LogInDTO.class))).thenReturn(Mono.just("token123"));

    Mono<ServerResponse> responseMono = handler.logIn(serverRequest);

    StepVerifier.create(responseMono)
      .assertNext(response -> {
        assert response.statusCode().equals(HttpStatus.OK);
        assert response.headers().getContentType().equals(MediaType.APPLICATION_JSON);
      })
      .verifyComplete();
  }
}
