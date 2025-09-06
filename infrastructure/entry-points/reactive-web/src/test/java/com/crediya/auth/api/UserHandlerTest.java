package com.crediya.auth.api;

import com.crediya.auth.model.user.User;
import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;

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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserHandlerTest {

  @InjectMocks
  private UserHandler userHandler;

  @Mock
  private UserUseCase useCase;

  @Mock
  private ServerRequest serverRequest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private static User createUser() {
    return User.builder()
      .userId(1L)
      .userRoleId(1)
      .firstName("John")
      .lastName("Dow")
      .email("john@example.com")
      .identityCardNumber("12345")
      .password("pass")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .birthDate(LocalDate.parse("2000-08-24"))
      .address("Street 123")
      .build();
  }

  @Test
  void testRegisterUser() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john@example.com")
      .identityCardNumber("12345")
      .password("pass")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .birthDate("2000-01-01")
      .address("Street 123")
      .build();

    User user = createUser();

    when(serverRequest.bodyToMono(RegisterUserDTO.class)).thenReturn(Mono.just(dto));
    when(useCase.registerUser(any(RegisterUserDTO.class))).thenReturn(Mono.just(user));

    Mono<ServerResponse> responseMono = userHandler.registerUser(serverRequest);

    StepVerifier.create(responseMono)
      .assertNext(response -> {
        assert response.statusCode().equals(HttpStatus.CREATED);
        assert response.headers().getContentType().equals(MediaType.APPLICATION_JSON);
      })
      .verifyComplete();
  }

  @Test
  void testGetUserByIdentityCardNumber() {
    String email = "john@example.com";
    User user = createUser();

    when(serverRequest.pathVariable("identity_card_number")).thenReturn(email);
    when(useCase.getUserByIdentityCardNumber(email)).thenReturn(Mono.just(user));

    Mono<ServerResponse> responseMono = userHandler.getUserByIdentityCardNumber(serverRequest);

    StepVerifier.create(responseMono)
      .assertNext(response -> {
        assert response.statusCode().equals(HttpStatus.OK);
        assert response.headers().getContentType().equals(MediaType.APPLICATION_JSON);
      })
      .verifyComplete();
  }
}
