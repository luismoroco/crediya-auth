package com.crediya.auth.usecase.user;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;
import com.crediya.auth.model.user.gateways.UserRepository;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.exc.NotFoundException;
import com.crediya.common.exc.ValidationException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class UserUseCase {

  private static final long MINIMUM_BASIC_WAGING = 0;
  private static final long MAXIMUM_BASIC_WAGING = 15000000;

  private final UserRepository repository;

  public Mono<User> registerUser(RegisterUserDTO dto) {
    return validateRegisterUserDTOConstraints(dto)
      .then(this.repository.existsByEmail(dto.getEmail()))
      .flatMap(userExists -> {
        if (Boolean.TRUE.equals(userExists)) {
          return Mono.error(
            new ValidationException(
              DomainError.ENTITY_ALREADY_EXISTS.get("EMAIL", dto.getEmail())
            ));
        }

        User user = new User();
        user.setUserRole(UserRole.USER);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setIdentityCardNumber(dto.getIdentityCardNumber());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setBasicWaging(dto.getBasicWaging());
        user.setBirthDate(LocalDate.parse(dto.getBirthDate()));
        user.setAddress(dto.getAddress());

        return this.repository.save(user);
      });
  }

  public Mono<User> getUserByEmail(String email) {
    return Validator.email("EMAIL", email)
      .then(this.repository.findByEmail(email))
      .switchIfEmpty(Mono.error(new NotFoundException(DomainError.ENTITY_NOT_FOUND.get("EMAIL", email))));
  }

  private static Mono<Void> validateRegisterUserDTOConstraints(RegisterUserDTO dto) {
    return Validator.string("FIRST NAME", dto.getFirstName())
      .then(Validator.string("LAST NAME", dto.getLastName()))
      .then(Validator.email("EMAIL", dto.getEmail()))
      .then(Validator.string("IDENTITY CARD NUMBER", dto.getIdentityCardNumber()))
      .then(Validator.string("PASSWORD", dto.getPassword()))
      .then(Validator.phone("PHONE NUMBER", dto.getPhoneNumber()))
      .then(Validator.string("ADDRESS", dto.getAddress()))
      .then(Validator.string("BIRTH DATE", dto.getAddress()))
      .then(Validator.numberBetween("BASIC WAGING", dto.getBasicWaging(), MINIMUM_BASIC_WAGING,
        MAXIMUM_BASIC_WAGING));
  }
}
