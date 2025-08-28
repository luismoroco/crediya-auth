package com.crediya.auth.r2dbc;

import com.crediya.auth.r2dbc.entity.UserEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class UserReactiveRepositoryTest {

  private UserReactiveRepository repository;

  @BeforeEach
  void setUp() {
    repository = Mockito.mock(UserReactiveRepository.class);
  }

  private static UserEntity createUserEntity() {
    return UserEntity.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john@example.com")
      .identityCardNumber("12345678")
      .password("pass123")
      .phoneNumber("999999999")
      .basicWaging(1000L)
      .userRoleId(2)
      .birthDate(LocalDate.of(1990, 1, 1))
      .address("Street 123")
      .build();
  }

  @Test
  void testSaveAndFindByEmail() {
    UserEntity userEntity = createUserEntity();

    when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
    when(repository.findByEmail("john@example.com")).thenReturn(Mono.just(userEntity));

    StepVerifier.create(repository.findByEmail("john@example.com"))
      .expectNextMatches(u -> u.getFirstName().equals("John"))
      .verifyComplete();
  }

  @Test
  void testExistsByEmail() {
    UserEntity userEntity = createUserEntity();

    when(repository.existsByEmail("john@example.com")).thenReturn(Mono.just(true));
    when(repository.existsByEmail("nonexistent@example.com")).thenReturn(Mono.just(false));

    StepVerifier.create(repository.existsByEmail("john@example.com"))
      .expectNext(true)
      .verifyComplete();

    StepVerifier.create(repository.existsByEmail("nonexistent@example.com"))
      .expectNext(false)
      .verifyComplete();
  }
}
