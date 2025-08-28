package com.crediya.auth.api;

import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
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
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

  private final Handler handler;
  private final GlobalExceptionFilter filter;

  @RouterOperations({
    @RouterOperation(
      path = "/api/v1/users",
      produces = { "application/json" },
      beanClass = Handler.class,
      method = RequestMethod.POST,
      beanMethod = "listenPOSTRegisterUser",
      operation = @Operation(
        operationId = "registerUser",
        summary = "Register new user",
        requestBody = @RequestBody(
          required = true,
          content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RegisterUserDTO.class),
            examples = {
              @ExampleObject(
                name = "Example",
                value = """
                                  {
                                    "firstName": "Juan",
                                    "lastName": "Pérez",
                                    "email": "juan.perez@correo.com",
                                    "identityCardNumber": "87654321",
                                    "password": "Secr3tPass!",
                                    "phoneNumber": "51987654321",
                                    "basicWaging": 3500,
                                    "address": "Av. Simp",
                                    "birthDate": "2000-08-24"
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
  public RouterFunction<ServerResponse> routerFunction() {
    return route(POST("/api/v1/users"), this.handler::registerUser)
      .andRoute(GET("/api/v1/users/{email}"), this.handler::getUserByEmail)
      .filter(filter);
  }
}
