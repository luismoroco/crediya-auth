package com.crediya.auth.r2dbc;

import com.crediya.auth.model.user.User;
import com.crediya.auth.r2dbc.entity.UserEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    private static UserEntity createEntity() {
        return UserEntity.builder()
          .userId(1L)
          .firstName("John")
          .lastName("Doe")
          .email("john@example.com")
          .identityCardNumber("12345")
          .password("pass")
          .phoneNumber("999999999")
          .basicWaging(1000L)
          .userRoleId(1)
          .birthDate(LocalDate.parse("2000-08-24"))
          .address("Street 123")
          .build();
    }

    private static User createUser() {
        return new User(1L, "John", "Doe", "john@example.com", "12345",
          "pass", "999999999", 1000L, 1, LocalDate.parse("2000-08-24"), "Street 123");
    }


    @Test
    void mustFindValueById() {
        UserEntity entity = createEntity();
        User user = createUser();

        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.findByUserId(1L);

        StepVerifier.create(result)
          .expectNextMatches(u -> u.getEmail().equals("john@example.com"))
          .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        UserEntity entity = createEntity();
        User user = createUser();

        when(repository.findAll()).thenReturn(Flux.just(entity));
        when(mapper.map(entity, User.class)).thenReturn(user);

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
          .expectNextMatches(u -> u.getEmail().equals("john@example.com"))
          .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        // TODO: Review weird behavior
        UserEntity entity = createEntity();
        User user = createUser();

        when(mapper.map(any(UserEntity.class), eq(User.class))).thenReturn(user);
        when(mapper.map(any(User.class), eq(UserEntity.class))).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
          .expectNextMatches(u -> u.getEmail().equals("john@example.com"))
          .verifyComplete();
    }
}
