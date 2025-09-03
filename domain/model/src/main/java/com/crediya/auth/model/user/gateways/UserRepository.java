package com.crediya.auth.model.user.gateways;

import com.crediya.auth.model.user.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository {
  Mono<User> findByUserId(Long userId);

  Mono<Boolean> existsByEmail(String email);

  Mono<User> save(User user);

  Mono<User> findByEmail(String email);

  Mono<User> findByIdentityCardNumber(String identityCardNumber);

  Mono<Boolean> existsByIdentityCardNumber(String identityCardNumber);

  Flux<User> findUsers(List<String> identityCardNumbers);
}
