package com.crediya.auth.usecase.user;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;
import com.crediya.auth.model.user.gateways.JwtProvider;
import com.crediya.auth.model.user.gateways.PasswordEncoder;
import com.crediya.auth.model.user.gateways.UserRepository;
import com.crediya.auth.usecase.user.dto.GetUsersDTO;
import com.crediya.auth.usecase.user.dto.LogInDTO;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.auth.usecase.user.exc.InvalidCredentialsException;
import com.crediya.common.exc.NotFoundException;
import com.crediya.common.exc.ValidationException;
import com.crediya.common.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

  private UserRepository repository;
  private UserUseCase userUseCase;
  private PasswordEncoder passwordEncoder;
  private JwtProvider jwtProvider;

  @BeforeEach
  void setUp() {
    repository = Mockito.mock(UserRepository.class);
    Logger logger = Mockito.mock(Logger.class);
    passwordEncoder = Mockito.mock(PasswordEncoder.class);
    jwtProvider = Mockito.mock(JwtProvider.class);
    userUseCase = new UserUseCase(jwtProvider, passwordEncoder, repository, logger);
  }

  @Test
  void testRegisterUserSuccess() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john@example.com")
      .identityCardNumber("12345678")
      .password("pass123")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .birthDate("1990-01-01")
      .address("Street 123")
      .build();

    when(repository.existsByEmail(dto.getEmail())).thenReturn(Mono.just(false));
    when(repository.existsByIdentityCardNumber(dto.getIdentityCardNumber())).thenReturn(Mono.just(false));
    when(repository.save(any(User.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

    StepVerifier.create(userUseCase.registerUser(dto))
      .assertNext(user -> {
        assert user.getFirstName().equals("John");
        assert user.getUserRole() == UserRole.CUSTOMER;
        assert user.getBasicWaging() == 1000L;
      })
      .verifyComplete();

    verify(repository, times(1)).existsByEmail(dto.getEmail());
    verify(repository, times(1)).save(any(User.class));
  }

  @Test
  void testRegisterUserEmailAlreadyExists() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john@example.com")
      .identityCardNumber("12345678")
      .password("pass123")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .birthDate("1990-01-01")
      .address("Street 123")
      .build();

    when(repository.existsByIdentityCardNumber(dto.getIdentityCardNumber()))
      .thenReturn(Mono.just(false));
    when(repository.existsByEmail(dto.getEmail()))
      .thenReturn(Mono.just(true));

    StepVerifier.create(userUseCase.registerUser(dto))
      .expectErrorMatches(throwable -> throwable instanceof ValidationException)
      .verify();

    verify(repository, times(1)).existsByEmail(dto.getEmail());
    verify(repository, times(0)).save(any());
  }

  @Test
  void testRegisterUserIdentityCardNumberAlreadyExists() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john@example.com")
      .identityCardNumber("12345678")
      .password("pass123")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .birthDate("1990-01-01")
      .address("Street 123")
      .build();

    when(repository.existsByEmail(dto.getEmail())).thenReturn(Mono.just(false));
    when(repository.existsByIdentityCardNumber(dto.getIdentityCardNumber())).thenReturn(Mono.just(true));

    StepVerifier.create(userUseCase.registerUser(dto))
      .expectErrorMatches(throwable -> throwable instanceof ValidationException
        && throwable.getMessage().contains(dto.getIdentityCardNumber()))
      .verify();
  }

  @Test
  void testGetUserByIdentityCardNumberSuccess() {
    User user = new User();
    user.setIdentityCardNumber("12343455");

    when(repository.findByIdentityCardNumber("12343455")).thenReturn(Mono.just(user));

    StepVerifier.create(userUseCase.getUserByIdentityCardNumber("12343455"))
      .expectNext(user)
      .verifyComplete();
  }

  @Test
  void testGetUserByIdentityCardNumberNotFound() {
    when(repository.findByIdentityCardNumber("12343455")).thenReturn(Mono.empty());

    StepVerifier.create(userUseCase.getUserByIdentityCardNumber("12343455"))
      .expectErrorMatches(throwable -> throwable instanceof NotFoundException &&
        throwable.getMessage().contains("not found"))
      .verify();
  }

  @Test
  void testValidateRegisterUserDTOConstraintsSuccess() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john@example.com")
      .identityCardNumber("12345678")
      .password("pass123")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .birthDate("1990-01-01")
      .address("Street 123")
      .build();

    StepVerifier.create(UserUseCase.validateRegisterUserDTOConstraints(dto))
      .verifyComplete();
  }

  @Test
  void testValidateRegisterUserDTOConstraintsInvalidEmail() {
    RegisterUserDTO dto = RegisterUserDTO.builder()
      .firstName("John")
      .lastName("Doe")
      .email("invalid-emailgmail.com")
      .identityCardNumber("12345678")
      .password("pass123")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .birthDate("1990-01-01")
      .address("Street 123")
      .build();

    StepVerifier.create(UserUseCase.validateRegisterUserDTOConstraints(dto))
      .expectErrorMatches(throwable -> throwable instanceof ValidationException &&
        throwable.getMessage().contains("invalid"))
      .verify();
  }

  @Test
  void testGetUsersSuccess() {
    GetUsersDTO dto = new GetUsersDTO();
    dto.setIdentityCardNumbers(List.of("12343455"));

    User user = new User();
    user.setIdentityCardNumber("12343455");

    when(repository.findUsers(dto.getIdentityCardNumbers())).thenReturn(Flux.just(user));

    StepVerifier.create(userUseCase.getUsers(dto))
      .expectNextMatches(u -> u.getIdentityCardNumber().equals("12343455"))
      .verifyComplete();

    verify(repository, times(1)).findUsers(dto.getIdentityCardNumbers());
  }

  @Test
  void testGetUsersNotFound() {
    GetUsersDTO dto = new GetUsersDTO();
    dto.setIdentityCardNumbers(List.of("12343455"));

    when(repository.findUsers(dto.getIdentityCardNumbers())).thenReturn(Flux.empty());

    StepVerifier.create(userUseCase.getUsers(dto))
      .expectNextCount(0)
      .verifyComplete();
  }

  @Test
  void testValidateGetUsersDTOConstraintsSuccess() {
    GetUsersDTO dto = new GetUsersDTO();
    dto.setIdentityCardNumbers(List.of("12343455"));

    StepVerifier.create(UserUseCase.validateGetUsersDTOConstraints(dto))
      .verifyComplete();
  }

  @Test
  void testLoginUserSuccess() {
    LogInDTO dto = new LogInDTO();
    dto.setEmail("root@gmail.com");
    dto.setPassword("pass123");

    User user = new User();
    user.setEmail("root@gmail.com");
    user.setPassword("encoded-pass123");

    when(repository.findByEmail(dto.getEmail())).thenReturn(Mono.just(user));
    when(passwordEncoder.matches(dto.getPassword(), "encoded-pass123")).thenReturn(true);
    when(jwtProvider.generate(user)).thenReturn("mocked-jwt-token");

    StepVerifier.create(userUseCase.logIn(dto))
      .expectNextMatches(token -> token.equals("mocked-jwt-token"))
      .verifyComplete();
  }

  @Test
  void testValidateLoginUserSuccess() {
    LogInDTO dto = new LogInDTO();
    dto.setEmail("root@gmail.com");
    dto.setPassword("pass123");

    StepVerifier.create(UserUseCase.validateLoginDTOConstraints(dto))
      .verifyComplete();
  }

  @Test
  void testLoginUserNotFound() {
    LogInDTO dto = new LogInDTO();
    dto.setEmail("root@gmail.com");
    dto.setPassword("pass123");

    when(repository.findByEmail(dto.getEmail())).thenReturn(Mono.empty());

    StepVerifier.create(userUseCase.logIn(dto))
      .expectErrorMatches(throwable -> throwable instanceof NotFoundException)
      .verify();
  }

  @Test
  void testLoginPasswordError() {
    LogInDTO dto = new LogInDTO();
    dto.setEmail("root@gmail.com");
    dto.setPassword("pass123");

    User user = new User();
    user.setEmail("root@gmail.com");
    user.setPassword("encoded-pass123");

    when(repository.findByEmail(dto.getEmail())).thenReturn(Mono.just(user));
    when(passwordEncoder.matches(dto.getPassword(), "encoded-pass123")).thenReturn(false);

    StepVerifier.create(userUseCase.logIn(dto))
      .expectErrorMatches(throwable -> throwable instanceof InvalidCredentialsException)
      .verify();
  }
}
