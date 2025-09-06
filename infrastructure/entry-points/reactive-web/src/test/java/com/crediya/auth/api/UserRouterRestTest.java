package com.crediya.auth.api;

import com.crediya.auth.api.config.RouterPathProperties;
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

class UserRouterRestTest {

  private UserUseCase useCase;
  private WebTestClient webTestClient;
  private RouterPathProperties routerPathProperties;
  private RouterPathProperties.UserPath userPath;

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
    routerPathProperties = new RouterPathProperties();
    userPath = new RouterPathProperties.UserPath();
    userPath.setRegisterUser("/api/v1/users");
    userPath.setGetUserByIdentityCardNumber("/api/v1/users/{identity_card_number}");
    userPath.setGetUsers("/api/v1/users");

    routerPathProperties.setUser(userPath);

    useCase = mock(UserUseCase.class);
    //routerPathProperties = new RouterPathProperties();
    UserHandler userHandler = new UserHandler(useCase);
    RouterFunction<?> routes = new UserRouterRest(userHandler, new GlobalExceptionFilter(), routerPathProperties)
      .userRouterFunction();
    webTestClient = WebTestClient.bindToRouterFunction(routes)
      .build();
  }

  @Test
  void mustRegisterUser() {
    User user = createUser();

    when(useCase.registerUser(org.mockito.ArgumentMatchers.any(RegisterUserDTO.class)))
      .thenReturn(Mono.just(user));

    webTestClient.post()
      .uri(userPath.getRegisterUser())
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
      .uri(userPath.getRegisterUser())
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createInvalidUserDTO())
      .exchange()
      .expectStatus().is4xxClientError();
  }

  @Test
  void mustGetUserByEmail() {
    User user = createUser();
    String email = user.getEmail();

    when(useCase.getUserByIdentityCardNumber(email))
      .thenReturn(Mono.just(user));

    webTestClient.get()
      .uri(userPath.getGetUserByIdentityCardNumber(), email)
      .exchange()
      .expectStatus().isOk()
      .expectBody()
      .jsonPath("$.email").isEqualTo(email)
      .jsonPath("$.firstName").isEqualTo(user.getFirstName());
  }

  @Test
  void mustNotGetUserByEmail() {
    String email = "not-registered-user@example.com";

    when(useCase.getUserByIdentityCardNumber(email))
      .thenReturn(Mono.error(new NotFoundException("Invalid email")));

    webTestClient.post()
      .uri(userPath.getGetUserByIdentityCardNumber(), email)
      .exchange()
      .expectStatus().is4xxClientError();
  }
}
