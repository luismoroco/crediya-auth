package com.crediya.auth.api;

import com.crediya.auth.api.dto.CreateUserRequest;
import com.crediya.auth.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class WebController {

  private final UserUseCase useCase;

  @PostMapping("")
  public Mono<ResponseEntity<String>> createUser(@RequestBody final CreateUserRequest request) {
    return this.useCase.createUser(request.map()).map(ResponseEntity::ok);
  }
}
