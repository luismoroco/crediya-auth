package com.crediya.auth.api;

import com.crediya.auth.api.config.RouterPathProperties;
import com.crediya.auth.usecase.user.dto.RegisterUserDTO;
import com.crediya.common.api.handling.GlobalExceptionFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class UserRouterRest {

  private final UserHandler handler;
  private final GlobalExceptionFilter filter;
  private final RouterPathProperties pathProperties;

  @RouterOperations({
    @RouterOperation(
      path = "/api/v1/users",
      produces = { "application/json" },
      beanClass = UserHandler.class,
      method = RequestMethod.POST,
      beanMethod = "registerUser",
      operation = @Operation(
        operationId = "registerUser",
        summary = "Register new user",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
          required = true,
          content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RegisterUserDTO.class),
            examples = {
              @ExampleObject(
                name = "Create new user",
                value = """
                                  {
                                    "firstName": "Juan",
                                    "lastName": "Pérez",
                                    "email": "juan.perez@correo.com",
                                    "identityCardNumber": "87654321",
                                    "password": "Secr3tPass!",
                                    "phoneNumber": "960329458",
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
    ),
    @RouterOperation(
      path = "/api/v1/users/{identity_card_number}",
      produces = { "application/json" },
      beanClass = UserHandler.class,
      method = RequestMethod.GET,
      beanMethod = "getUserByIdentityCardNumber",
      operation = @Operation(
        operationId = "getUserByIdentityCardNumber",
        summary = "Get user by identity card number",
        parameters = {
          @Parameter(
            name = "identity_card_number",
            in = ParameterIn.PATH,
            required = true,
            description = "User's identity card number",
            schema = @Schema(type = "string", example = "87654321")
          )
        },
        responses = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "User not found")
        }
      )
    ),
    @RouterOperation(
      path = "/api/v1/users",
      produces = { "application/json" },
      beanClass = UserHandler.class,
      method = RequestMethod.GET,
      beanMethod = "getUsers",
      operation = @Operation(
        operationId = "getUsers",
        summary = "Get users",
        parameters = {
          @Parameter(
            name = "identity_card_numbers",
            in = ParameterIn.QUERY,
            required = true,
            description = "Users' identity card number",
            array = @ArraySchema(schema = @Schema(type = "string")),
            example = "87654321"
          )
        },
        responses = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
        }
      )
    )
  })
  @Bean
  public RouterFunction<ServerResponse> userRouterFunction() {
    RouterPathProperties.UserPath userPath = pathProperties.getUser();

    return route(POST(userPath.getRegisterUser()), this.handler::registerUser)
      .andRoute(GET(userPath.getGetUserByIdentityCardNumber()), this.handler::getUserByIdentityCardNumber)
      .andRoute(GET(userPath.getGetUsers()), this.handler::getUsers)
      .filter(filter);
  }
}
