package com.crediya.auth.api.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class JwtFilterTest {

  private JwtFilter filter;
  private WebFilterChain chain;

  @BeforeEach
  void setUp() {
    filter = new JwtFilter();
    chain = mock(WebFilterChain.class);
    when(chain.filter(any())).thenReturn(Mono.empty());
  }

  @Test
  void shouldSkipIfPathContainsAuth() {
    MockServerWebExchange exchange = MockServerWebExchange.from(
      MockServerHttpRequest.get("/api/auth/login").build()
    );

    filter.filter(exchange, chain).block();

    assertFalse(exchange.getAttributes().containsKey("token"));
    verify(chain, times(1)).filter(exchange);
  }

  @Test
  void shouldExtractTokenWhenAuthorizationHeaderIsPresent() {
    String token = "abc123";
    MockServerWebExchange exchange = MockServerWebExchange.from(
      MockServerHttpRequest.get("/api/secure/resource")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .build()
    );

    filter.filter(exchange, chain).block();

    assertEquals(token, exchange.getAttribute("token"));
    verify(chain, times(1)).filter(exchange);
  }

  @Test
  void shouldNotAddTokenIfAuthorizationHeaderIsMissing() {
    MockServerWebExchange exchange = MockServerWebExchange.from(
      MockServerHttpRequest.get("/api/secure/resource").build()
    );

    filter.filter(exchange, chain).block();

    assertNull(exchange.getAttribute("token"));
    verify(chain, times(1)).filter(exchange);
  }

  @Test
  void shouldNotAddTokenIfAuthorizationHeaderIsInvalid() {
    MockServerWebExchange exchange = MockServerWebExchange.from(
      MockServerHttpRequest.get("/api/secure/resource")
        .header(HttpHeaders.AUTHORIZATION, "Invalid abc123")
        .build()
    );

    filter.filter(exchange, chain).block();

    assertNull(exchange.getAttribute("token"));
    verify(chain, times(1)).filter(exchange);
  }
}
