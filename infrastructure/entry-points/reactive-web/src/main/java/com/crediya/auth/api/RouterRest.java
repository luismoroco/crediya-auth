package com.crediya.auth.api;

import com.crediya.common.api.handling.GlobalExceptionFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

  @Bean
  public RouterFunction<ServerResponse> userRouterFunction(Handler handler, GlobalExceptionFilter filter) {
    return route(POST("/api/v1/users"), handler::listenPOSTSignUp)
      .filter(filter);
  }
}
