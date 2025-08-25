package com.crediya.auth.api;

import com.crediya.common.exc.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidationService {
  private final Validator validator;

  public <T> Mono<T> validate(T obj) {
    return Mono.fromCallable(() -> validator.validate(obj))
      .flatMap(violations -> violations.isEmpty()
          ? Mono.just(obj)
          : Mono.error(new BadRequestException(
            "Validation error",
            violations.stream()
              .collect(Collectors.toMap(
                v -> v.getPropertyPath().toString(),
                ConstraintViolation::getMessage
              ))
          )
      ));
  }
}
