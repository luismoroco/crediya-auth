package com.crediya.auth.api;

import com.crediya.auth.model.user.User;
import com.crediya.auth.usecase.user.UserUseCase;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.api.handling.GlobalExceptionFilter;
import com.crediya.common.exc.NotFoundException;
import com.crediya.common.exc.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RouterRestTest {

  private UserUseCase useCase;
  private WebTestClient webTestClient;

  private static User createUser() {
    return new User(1L, "John", "Doe", "john@example.com", "12345",
      "pass", "999999999", 1000L, 1, LocalDate.parse("2000-08-24"), "Street 123");
  }

  private RegisterUserDTO createUserDTO() {
    return RegisterUserDTO.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john@example.com")
      .identityCardNumber("12345")
      .password("pass")
      .phoneNumber("999999")
      .basicWaging(1000L)
      .birthDate("2000-08-24")
      .address("Street 123")
      .build();
  }

  private RegisterUserDTO createInvalidUserDTO() {
    return RegisterUserDTO.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john-123123123-dfgexamplecom")
      .identityCardNumber("12345")
      .password("pass")
      .phoneNumber("999999")
      .basicWaging(1000L)
      .birthDate("2000-08-242")
      .address("Street 123")
      .build();
  }

  @BeforeEach
  void setUp() {
    useCase = mock(UserUseCase.class);
    Handler handler = new Handler(useCase);
    RouterFunction<?> routes = new RouterRest(handler)
      .routerFunction(new GlobalExceptionFilter());
    webTestClient = WebTestClient.bindToRouterFunction(routes)
      .build();
  }

  @Test
  void mustRegisterUser() {
    User user = createUser();

    when(useCase.registerUser(org.mockito.ArgumentMatchers.any(RegisterUserDTO.class)))
      .thenReturn(Mono.just(user));

    webTestClient.post()
      .uri("/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createUserDTO())
      .exchange()
      .expectStatus().isCreated();
  }

  @Test
  void mustNotRegisterUser() {
    when(useCase.registerUser(org.mockito.ArgumentMatchers.any(RegisterUserDTO.class)))
      .thenReturn(Mono.error(new ValidationException("Invalid User")));

    webTestClient.post()
      .uri("/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createInvalidUserDTO())
      .exchange()
      .expectStatus().is4xxClientError();
  }

  @Test
  void mustGetUserByEmail() {
    User user = createUser();
    String email = user.getEmail();

    when(useCase.getUserByEmail(email))
      .thenReturn(Mono.just(user));

    webTestClient.get()
      .uri("/api/v1/users/{email}", email)
      .exchange()
      .expectStatus().isOk()
      .expectBody()
      .jsonPath("$.email").isEqualTo(email)
      .jsonPath("$.firstName").isEqualTo(user.getFirstName());
  }

  @Test
  void mustNotGetUserByEmail() {
    String email = "not-registered-user@example.com";

    when(useCase.getUserByEmail(email))
      .thenReturn(Mono.error(new NotFoundException("Invalid email")));

    webTestClient.post()
      .uri("/api/v1/users/{email}", email)
      .exchange()
      .expectStatus().is4xxClientError();
  }
}
