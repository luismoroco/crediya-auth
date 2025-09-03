package com.crediya.auth.usecase.user.exc;

import com.crediya.common.exc.BadRequestException;

import java.util.Map;

public class InvalidCredentialsException extends BadRequestException {
  public InvalidCredentialsException(String message, Map<String, Object> body) {
    super(message, body);
  }

  public InvalidCredentialsException(String message) {
    super(message);
  }
}
