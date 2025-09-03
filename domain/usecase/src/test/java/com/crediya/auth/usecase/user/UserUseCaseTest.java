package com.crediya.auth.usecase.user;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;
import com.crediya.auth.model.user.gateways.JwtProvider;
import com.crediya.auth.model.user.gateways.PasswordEncoder;
import com.crediya.auth.model.user.gateways.UserRepository;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.exc.NotFoundException;
import com.crediya.common.exc.ValidationException;
import com.crediya.common.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

  private Logger logger;
  private UserRepository repository;
  private UserUseCase userUseCase;
  private PasswordEncoder PasswordEncoder;
  private JwtProvider jwtProvider;

  @BeforeEach
  void setUp() {
    repository = Mockito.mock(UserRepository.class);
    logger = Mockito.mock(Logger.class);
    PasswordEncoder = Mockito.mock(PasswordEncoder.class);
    jwtProvider = Mockito.mock(JwtProvider.class);
    userUseCase = new UserUseCase(jwtProvider, PasswordEncoder, repository, logger);
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
      .email("john@example.com")
      .build();

    when(repository.existsByEmail(dto.getEmail())).thenReturn(Mono.just(true));

    StepVerifier.create(userUseCase.registerUser(dto))
      .expectErrorMatches(throwable -> throwable instanceof ValidationException)
      .verify();

    verify(repository, times(1)).existsByEmail(dto.getEmail());
    verify(repository, times(0)).save(any());
  }

  @Test
  void testGetUserByIdentityCardNumberSuccess() {
    User user = new User();
    user.setEmail("john@example.com");

    when(repository.findByEmail("john@example.com")).thenReturn(Mono.just(user));

    StepVerifier.create(userUseCase.getUserByIdentityCardNumber("john@example.com"))
      .expectNext(user)
      .verifyComplete();
  }

  @Test
  void testGetUserByIdentityCardNumberNotFound() {
    when(repository.findByEmail("notfound@example.com")).thenReturn(Mono.empty());

    StepVerifier.create(userUseCase.getUserByIdentityCardNumber("notfound@example.com"))
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
}
