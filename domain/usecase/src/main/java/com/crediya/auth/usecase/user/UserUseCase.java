package com.crediya.auth.usecase.user;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;
import com.crediya.auth.model.user.gateways.JwtProvider;
import com.crediya.auth.model.user.gateways.PasswordEncoder;
import com.crediya.auth.model.user.gateways.UserRepository;
import com.crediya.auth.usecase.user.dto.GetUsersDTO;
import com.crediya.auth.usecase.user.dto.LogInDTO;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.auth.usecase.user.exc.InvalidCredentialsException;
import com.crediya.common.exc.NotFoundException;
import com.crediya.common.exc.ValidationException;
import com.crediya.common.logging.Logger;
import com.crediya.common.validation.ValidatorUtils;
import static com.crediya.auth.model.user.User.Field.*;
import static com.crediya.common.LogCatalog.*;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class UserUseCase {

  private static final long MINIMUM_BASIC_WAGING = 0;
  private static final long MAXIMUM_BASIC_WAGING = 15000000;

  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository repository;
  private final Logger logger;

  public Mono<User> registerUser(RegisterUserDTO dto) {
    return validateRegisterUserDTOConstraints(dto)
      .then(this.repository.existsByIdentityCardNumber(dto.getIdentityCardNumber()))
      .flatMap(identityCardNumberExists -> {
        if (Boolean.TRUE.equals(identityCardNumberExists)) {
          this.logger.warn(ENTITY_ALREADY_EXISTS.of(IDENTITY_CARD_NUMBER, dto.getIdentityCardNumber()));
          return Mono.error(new ValidationException(ENTITY_ALREADY_EXISTS.of(
            IDENTITY_CARD_NUMBER, dto.getIdentityCardNumber())));
        }

        return Mono.empty();
      })
      .then(this.repository.existsByEmail(dto.getEmail()))
      .flatMap(userExists -> {
        if (Boolean.TRUE.equals(userExists)) {
          this.logger.warn(ENTITY_ALREADY_EXISTS.of(EMAIL, dto.getEmail()));
          return Mono.error(new ValidationException(ENTITY_ALREADY_EXISTS.of(EMAIL, dto.getEmail())));
        }

        return Mono.empty();
      })
      .then(Mono.defer(() -> {
        User user = new User();
        user.setUserRole(UserRole.CUSTOMER);
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
      }))
      .doOnError(error -> this.logger.error(ERROR_PROCESSING.of("registerUser", dto.getEmail()),
        error)
      );
  }

  public Mono<User> getUserByIdentityCardNumber(String identityCardNumber) {
    return ValidatorUtils.string(IDENTITY_CARD_NUMBER, identityCardNumber)
      .then(this.repository.findByIdentityCardNumber(identityCardNumber))
      .switchIfEmpty(Mono.defer(() -> {
        this.logger.warn(ENTITY_NOT_FOUND.of(IDENTITY_CARD_NUMBER, identityCardNumber));
        return Mono.error(new NotFoundException(ENTITY_NOT_FOUND.of(IDENTITY_CARD_NUMBER, identityCardNumber)));
      }))
      .doOnError(error -> this.logger.error(ERROR_PROCESSING.of("getUserByIdentityCardNumber",
        identityCardNumber), error)
      );
  }

  public Flux<User> getUsers(GetUsersDTO dto) {
    return validateGetUsersDTOConstraints(dto)
      .thenMany(this.repository.findUsers(dto.getIdentityCardNumbers()))
      .switchIfEmpty(Flux.defer(() -> {
        this.logger.warn(ENTITY_NOT_FOUND.of("Users", dto));
        return Flux.empty();
      }));
  }

  public Mono<String> logIn(LogInDTO dto) {
    return validateLoginDTOConstraints(dto)
      .then(this.repository.findByEmail(dto.getEmail()))
      .switchIfEmpty(Mono.defer(() -> {
        this.logger.warn(ENTITY_NOT_FOUND.of(EMAIL, dto.getEmail()));
        return Mono.error(new NotFoundException(ENTITY_NOT_FOUND.of(EMAIL, dto.getEmail())));
      }))
      .flatMap(user -> {
        if (!this.passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
          this.logger.warn(INVALID_PARAMETER.of(PASSWORD, dto.getPassword()));
          return Mono.error(new InvalidCredentialsException(INVALID_PARAMETER.of(PASSWORD, dto.getPassword())));
        }

        return Mono.just(this.jwtProvider.generate(user));
      });
  }

  public static Mono<Void> validateLoginDTOConstraints(LogInDTO dto) {
    return ValidatorUtils.string(EMAIL, dto.getEmail())
      .then(ValidatorUtils.string(PASSWORD, dto.getPassword()));
  }

  public static Mono<Void> validateGetUsersDTOConstraints(GetUsersDTO dto) {
    return ValidatorUtils.nonNull("IDENTITY CARD NUMBERS", dto.getIdentityCardNumbers());
  }

  public static Mono<Void> validateRegisterUserDTOConstraints(RegisterUserDTO dto) {
    return ValidatorUtils.string(FIRST_NAME, dto.getFirstName())
      .then(ValidatorUtils.string(LAST_NAME, dto.getLastName()))
      .then(ValidatorUtils.email(EMAIL, dto.getEmail()))
      .then(ValidatorUtils.string(IDENTITY_CARD_NUMBER, dto.getIdentityCardNumber()))
      .then(ValidatorUtils.string(PASSWORD, dto.getPassword()))
      .then(ValidatorUtils.phone(PHONE_NUMBER, dto.getPhoneNumber()))
      .then(ValidatorUtils.string(ADDRESS, dto.getAddress()))
      .then(ValidatorUtils.localDate(BIRTH_DATE, dto.getBirthDate()))
      .then(ValidatorUtils.numberBetween(BASIC_WAGING, dto.getBasicWaging(), MINIMUM_BASIC_WAGING,
        MAXIMUM_BASIC_WAGING));
  }
}
