package com.crediya.auth.usecase.user;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;
import com.crediya.auth.model.user.gateways.UserRepository;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.exc.NotFoundException;
import com.crediya.common.exc.ValidationException;
import com.crediya.common.logging.Logger;
import com.crediya.common.validation.ValidatorUtils;
import static com.crediya.auth.model.user.User.Field.*;
import static com.crediya.common.LogCatalog.*;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class UserUseCase {

  private static final long MINIMUM_BASIC_WAGING = 0;
  private static final long MAXIMUM_BASIC_WAGING = 15000000;

  private final UserRepository repository;
  private final Logger logger;

  public Mono<User> registerUser(RegisterUserDTO dto) {
    return validateRegisterUserDTOConstraints(dto)
      .then(this.repository.existsByEmail(dto.getEmail()))
      .flatMap(userExists -> {
        if (Boolean.TRUE.equals(userExists)) {
          this.logger.info(ENTITY_ALREADY_EXISTS.get(EMAIL.getLabel(), dto.getEmail()));
          return Mono.error(new ValidationException(ENTITY_ALREADY_EXISTS.get(EMAIL.getLabel(), dto.getEmail())));
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

        return this.repository.save(user)
          .doOnSuccess(saved -> this.logger.info(SUCCESSFUL_PROCESSING.get("registerUser", user.getUserId())))
          .doOnError(error -> this.logger.error(ERROR_PROCESSING.get("registerUser", user.getEmail()), error));
      });
  }

  public Mono<User> getUserByEmail(String email) {
    return ValidatorUtils.email(EMAIL.getLabel(), email)
      .then(this.repository.findByEmail(email))
      .switchIfEmpty(Mono.defer(() -> {
        this.logger.warn(ENTITY_NOT_FOUND.get(EMAIL.getLabel(), email));
        return Mono.error(new NotFoundException(ENTITY_NOT_FOUND.get(EMAIL.name(), email)));
      }))
      .doOnSuccess(user -> this.logger.info(SUCCESSFUL_PROCESSING.get("getUserByEmail", user.getUserId())))
      .doOnError(error -> this.logger.error(ERROR_PROCESSING.get("getUserByEmail", email), error));
  }

  public static Mono<Void> validateRegisterUserDTOConstraints(RegisterUserDTO dto) {
    return ValidatorUtils.string(FIRST_NAME.getLabel(), dto.getFirstName())
      .then(ValidatorUtils.string(LAST_NAME.getLabel(), dto.getLastName()))
      .then(ValidatorUtils.email(EMAIL.getLabel(), dto.getEmail()))
      .then(ValidatorUtils.string(IDENTITY_CARD_NUMBER.getLabel(), dto.getIdentityCardNumber()))
      .then(ValidatorUtils.string(PASSWORD.getLabel(), dto.getPassword()))
      .then(ValidatorUtils.phone(PHONE_NUMBER.getLabel(), dto.getPhoneNumber()))
      .then(ValidatorUtils.string(ADDRESS.getLabel(), dto.getAddress()))
      .then(ValidatorUtils.string(BIRTH_DATE.getLabel(), dto.getAddress()))
      .then(ValidatorUtils.numberBetween(BASIC_WAGING.getLabel(), dto.getBasicWaging(), MINIMUM_BASIC_WAGING,
        MAXIMUM_BASIC_WAGING));
  }
}
