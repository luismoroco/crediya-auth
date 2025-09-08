package com.crediya.auth.api.exc;

import com.crediya.common.exc.BadRequestException;

import java.util.Map;

public class InvalidTokenException extends BadRequestException {
  public InvalidTokenException(String message, Map<String, Object> body) {
    super(message, body);
  }

  public InvalidTokenException(String message) {
    super(message);
  }
}
