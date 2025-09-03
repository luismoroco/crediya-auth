package com.crediya.auth.r2dbc;

import com.crediya.auth.r2dbc.entity.UserEntity;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {
  Mono<Boolean> existsByEmail(String email);

  Mono<UserEntity> findByEmail(String email);

  Mono<UserEntity> findByIdentityCardNumber(String identityCardNumber);

  Mono<Boolean> existsByIdentityCardNumber(String identityCardNumber);

  @Query("" +
    "SELECT * FROM users " +
    "WHERE " +
      "identity_card_number IN (:identity_card_numbers)"
  )
  Flux<UserEntity> findUsers(@Param("identity_card_numbers") List<String> identityCardNumbers);
}
