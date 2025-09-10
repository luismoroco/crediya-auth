package com.crediya.auth.r2dbc.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ReactiveTransactionTest {

  @Mock
  private ReactiveTransactionManager reactiveTransactionManager;

  @Mock
  private TransactionalOperator transactionalOperator;

  @Test
  void shouldApplyTransactionToMono() {
    ReactiveTransaction transaction = new ReactiveTransaction(reactiveTransactionManager);
    Mono<String> inputMono = Mono.just("test");

    transaction.init(inputMono);
  }

  @Test
  void shouldApplyTransactionToFlux() {
    ReactiveTransaction transaction = new ReactiveTransaction(reactiveTransactionManager);
    Flux<String> inputFlux = Flux.just("test");

    transaction.init(inputFlux);
  }
}
