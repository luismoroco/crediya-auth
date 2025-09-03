package com.crediya.auth.api;

import com.crediya.common.api.handling.GlobalExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class AuthRouterRest {

  private final AuthHandler handler;
  private final GlobalExceptionFilter filter;

  @Bean
  public RouterFunction<ServerResponse> authRouterFunction() {
    return route(POST("/api/v1/auth/log-in"), this.handler::logIn)
      .andRoute(POST("/api/v1/auth/sign-up"), this.handler::logIn)
      .filter(filter);
  }
}
