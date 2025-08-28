package com.crediya.auth.usecase.user;

import com.crediya.common.EmailUtils;
import com.crediya.common.PhoneUtils;
import com.crediya.common.exc.ValidationException;

import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Validator {

  private Validator() {
  }

  public static Mono<Void> string(String entityName, String value) {
    if (Objects.isNull(value) || value.isBlank()) {
      return Mono.error(new ValidationException(DomainError.INVALID_PARAMETER.get(entityName, value)));
    }

    return Mono.empty();
  }

  public static Mono<Void> phone(String entityName, String value) {
    if (!PhoneUtils.isValid(value)) {
      return Mono.error(new ValidationException(DomainError.INVALID_PARAMETER.get(entityName, value)));
    }

    return Mono.empty();
  }

  public static Mono<Void> email(String entityName, String value) {
    if (!EmailUtils.isValid(value)) {
      return Mono.error(new ValidationException(DomainError.INVALID_PARAMETER.get(entityName, value)));
    }

    return Mono.empty();
  }

  public static Mono<Void> localDate(String entityName, String value) {
    return string(entityName, value)
      .then(Mono.defer(() -> {
        try {
          LocalDate.parse(value);
          return Mono.empty();
        } catch (DateTimeParseException e) {
          return Mono.error(new ValidationException(
            DomainError.INVALID_PARAMETER.get(entityName, value)
          ));
        }
      }));
  }

  public static Mono<Void> nonNull(String entityName, Object value) {
    if (Objects.isNull(value)) {
      return Mono.error(new ValidationException(
        DomainError.INVALID_PARAMETER.get(entityName, null)
      ));
    }
    return Mono.empty();
  }

  public static <T extends Comparable<T>> Mono<Void> numberBetween(
    String entityName,
    T value,
    T start,
    T end
  ) {
    return nonNull(entityName, value)
      .then(Mono.defer(() -> {
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
          return Mono.error(new ValidationException(DomainError.INVALID_PARAMETER.get(entityName, value)));
        }

        return Mono.empty();
      }));
  }
}
