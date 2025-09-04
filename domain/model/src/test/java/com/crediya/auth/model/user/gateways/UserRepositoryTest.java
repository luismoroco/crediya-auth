package com.crediya.auth.model.user.gateways;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class UserRepositoryTest {

  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    userRepository = Mockito.mock(UserRepository.class);
  }

  @Test
  void testFindByUserId() {
    User user = new User();
    user.setUserId(1L);
    user.setUserRole(UserRole.CUSTOMER);

    when(userRepository.findByUserId(1L)).thenReturn(Mono.just(user));

    StepVerifier.create(userRepository.findByUserId(1L))
      .expectNextMatches(u -> u.getUserId().equals(1L) && u.getUserRole() == UserRole.CUSTOMER)
      .verifyComplete();

    verify(userRepository, times(1)).findByUserId(1L);
  }

  @Test
  void testExistsByEmail() {
    when(userRepository.existsByEmail("john@example.com")).thenReturn(Mono.just(true));

    StepVerifier.create(userRepository.existsByEmail("john@example.com"))
      .expectNext(true)
      .verifyComplete();

    verify(userRepository, times(1)).existsByEmail("john@example.com");
  }

  @Test
  void testSaveUser() {
    User user = new User();
    user.setEmail("john@example.com");

    when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

    StepVerifier.create(userRepository.save(user))
      .expectNextMatches(u -> u.getEmail().equals("john@example.com"))
      .verifyComplete();

    verify(userRepository, times(1)).save(user);
  }

  @Test
  void testFindByEmail() {
    User user = new User();
    user.setEmail("john@example.com");

    when(userRepository.findByEmail("john@example.com")).thenReturn(Mono.just(user));

    StepVerifier.create(userRepository.findByEmail("john@example.com"))
      .expectNextMatches(u -> u.getEmail().equals("john@example.com"))
      .verifyComplete();

    verify(userRepository, times(1)).findByEmail("john@example.com");
  }

  @Test
  void testFindByIdentityCardNumber() {
    User user = new User();
    user.setIdentityCardNumber("12345678");

    when(userRepository.findByIdentityCardNumber("12345678")).thenReturn(Mono.just(user));

    StepVerifier.create(userRepository.findByIdentityCardNumber("12345678"))
      .expectNextMatches(u -> u.getIdentityCardNumber().equals("12345678"))
      .verifyComplete();

    verify(userRepository, times(1)).findByIdentityCardNumber("12345678");
  }

  @Test
  void testExistsByIdentityCardNumber() {
    when(userRepository.existsByIdentityCardNumber("12345678")).thenReturn(Mono.just(true));

    StepVerifier.create(userRepository.existsByIdentityCardNumber("12345678"))
      .expectNext(true)
      .verifyComplete();

    verify(userRepository, times(1)).existsByIdentityCardNumber("12345678");
  }

  @Test
  void testFindUsers() {
    User user1 = new User();
    user1.setIdentityCardNumber("12345678");

    User user2 = new User();
    user2.setIdentityCardNumber("87654321");

    when(userRepository.findUsers(List.of("12345678", "87654321")))
      .thenReturn(Flux.just(user1, user2));

    StepVerifier.create(userRepository.findUsers(List.of("12345678", "87654321")))
      .expectNextMatches(u -> u.getIdentityCardNumber().equals("12345678"))
      .expectNextMatches(u -> u.getIdentityCardNumber().equals("87654321"))
      .verifyComplete();

    verify(userRepository, times(1)).findUsers(List.of("12345678", "87654321"));
  }
}
