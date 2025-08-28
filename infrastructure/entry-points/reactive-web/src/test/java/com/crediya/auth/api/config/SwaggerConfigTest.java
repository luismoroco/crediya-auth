package com.crediya.auth.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

  private final SwaggerConfig swaggerConfig = new SwaggerConfig();

  @Test
  void apiInfoShouldReturnConfiguredOpenAPI() {
    OpenAPI openAPI = swaggerConfig.apiInfo();

    assertNotNull(openAPI, "OpenAPI bean should not be null");

    Info info = openAPI.getInfo();
    assertNotNull(info, "Info should not be null");

    assertEquals("com.crediya.auth.api", info.getTitle());
    assertEquals("crediya-auth", info.getDescription());
    assertEquals("1.0.0", info.getVersion());

    Contact contact = info.getContact();
    assertNotNull(contact, "Contact should not be null");
    assertEquals("Luis Moroco", contact.getName());
    assertEquals("lmoroco@crediya.com", contact.getEmail());
  }
}
