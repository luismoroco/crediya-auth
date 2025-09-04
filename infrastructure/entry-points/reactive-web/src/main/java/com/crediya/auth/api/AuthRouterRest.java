package com.crediya.auth.api;

import com.crediya.auth.usecase.user.dto.LogInDTO;
import com.crediya.common.api.handling.GlobalExceptionFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class AuthRouterRest {

  private final AuthHandler handler;
  private final GlobalExceptionFilter filter;

  @RouterOperations({
    @RouterOperation(
      path = "/api/v1/auth/log-in",
      produces = { "application/json" },
      beanClass = AuthHandler.class,
      method = RequestMethod.POST,
      beanMethod = "logIn",
      operation = @Operation(
        operationId = "logIn",
        summary = "Log-in account",
        requestBody = @RequestBody(
          required = true,
          content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = LogInDTO.class),
            examples = {
              @ExampleObject(
                name = "Log in account",
                value = """
                                  {
                                    "email": "root@gmail.com",
                                    "password": "root"
                                  }
                                  """
              )
            }
          )
        ),
        responses = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
        }
      )
    )
  })
  @Bean
  public RouterFunction<ServerResponse> authRouterFunction() {
    return route(POST("/api/v1/auth/log-in"), this.handler::logIn)
      .filter(filter);
  }
}
