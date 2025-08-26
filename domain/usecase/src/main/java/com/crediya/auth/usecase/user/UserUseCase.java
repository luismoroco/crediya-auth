package com.crediya.auth.usecase.user;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.gateways.UserRepository;
import com.crediya.auth.usecase.user.dto.SignUpDTO;
import com.crediya.common.exc.BadRequestException;
import com.crediya.common.transaction.Transaction;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

  private final UserRepository repository;
  private final Transaction transaction;

  public Mono<User> signUp(SignUpDTO dto) {
    return this.transaction.init(
      this.repository.existsByEmail(dto.getEmail())
        .flatMap(userExist -> {
          if (userExist) {
            return Mono.error(new BadRequestException("Email already exists"));
          }

          User user = new User();
          user.setUserRole(dto.getUserRole());
          user.setFirstName(dto.getFirstName());
          user.setLastName(dto.getLastName());
          user.setEmail(dto.getEmail());
          user.setIdentityCardNumber(dto.getIdentityCardNumber());
          user.setPassword(dto.getPassword());
          user.setPhoneNumber(dto.getPhoneNumber());
          user.setBasicWaging(dto.getBasicWaging());

          return this.repository.save(user);
        })
    );
  }
}
