package com.crediya.auth.usecase.user;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.UserRole;
import com.crediya.auth.model.user.gateways.UserRepository;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.EmailUtils;
import com.crediya.common.PhoneUtils;
import com.crediya.common.exc.NotFoundException;
import com.crediya.common.exc.ValidationException;
import com.crediya.common.transaction.Transaction;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.Objects;

@RequiredArgsConstructor
public class UserUseCase {

  private static final int MINIMUM_BASIC_WAGING = 0;
  private static final int MAXIMUM_BASIC_WAGING = 15000000;
  private static final int MINIMUM_AGE = 18;

  private final UserRepository repository;
  private final Transaction transaction;

  public Mono<User> registerUser(RegisterUserDTO dto) {
    validateRegisterUserRequest(dto);

    return this.transaction.init(
      this.repository.existsByEmail(dto.getEmail())
        .flatMap(userExist -> {
          if (userExist) {
            return Mono.error(new ValidationException("Email already exists"));
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
          user.setBirthDate(dto.getBirthDate());
          user.setAddress(dto.getAddress());

          return this.repository.save(user);
        })
    );
  }

  public Mono<User> getUserByEmail(String email) {
    if (Objects.isNull(email)) {
      throw new ValidationException("Invalid email");
    }

    return this.repository.findByEmail(email)
      .switchIfEmpty(Mono.error(new NotFoundException("Invalid email")));
  }

  private void validateRegisterUserRequest(RegisterUserDTO dto) {
    if (Objects.isNull(dto.getFirstName()) || dto.getFirstName().isBlank()) {
      throw new ValidationException("Invalid first name");
    }

    if (Objects.isNull(dto.getLastName()) || dto.getLastName().isBlank()) {
      throw new ValidationException("Invalid last name");
    }

    if (Objects.isNull(dto.getPhoneNumber()) || dto.getPhoneNumber().isBlank()) {
      throw new ValidationException("Invalid phone number");
    }

    if (!EmailUtils.isValid(dto.getEmail())) {
      throw new ValidationException("Invalid email");
    }

    if (Objects.isNull(dto.getIdentityCardNumber()) || dto.getIdentityCardNumber().isBlank()) {
      throw new ValidationException("Invalid identity card number");
    }

    if (Objects.isNull(dto.getPassword()) || dto.getPassword().isBlank()) {
      throw new ValidationException("Invalid password");
    }

    if (!PhoneUtils.isValid(dto.getPhoneNumber())) {
      throw new ValidationException("Invalid phone number");
    }

    if (Objects.isNull(dto.getBasicWaging()) ||
      dto.getBasicWaging() < MINIMUM_BASIC_WAGING ||
      dto.getBasicWaging() > MAXIMUM_BASIC_WAGING) {
      throw new ValidationException("Invalid basic waging");
    }

    if (Objects.isNull(dto.getBirthDate()) ||
      dto.getBirthDate().isAfter(LocalDate.now().minusYears(MINIMUM_AGE))) {
      throw new ValidationException("Invalid birth date");
    }

    if (Objects.isNull(dto.getAddress()) || dto.getAddress().isBlank()) {
      throw new ValidationException("Invalid address");
    }
  }
}
