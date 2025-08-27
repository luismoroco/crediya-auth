package com.crediya.auth.model.user.gateways;

import com.crediya.auth.model.user.User;

import reactor.core.publisher.Mono;

public interface UserRepository {
  Mono<User> findByUserId(Long userId);

  Mono<Boolean> existsByEmail(String email);

  Mono<User> save(User user);
}
