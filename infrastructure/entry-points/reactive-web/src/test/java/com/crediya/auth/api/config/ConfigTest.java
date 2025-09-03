package com.crediya.auth.api.config;

import com.crediya.auth.api.UserHandler;
import com.crediya.auth.api.UserRouterRest;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {UserRouterRest.class, UserHandler.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private UserUseCase userUseCase;
//
//    @MockBean
//    private GlobalExceptionFilter globalExceptionFilter;
//
//    @Test
//    void corsConfigurationShouldAllowOrigins() {
//        this.webTestClient.get()
//                .uri("/health")
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().valueEquals("Content-Security-Policy",
//                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
//                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
//                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
//                .expectHeader().valueEquals("Server", "")
//                .expectHeader().valueEquals("Cache-Control", "no-store")
//                .expectHeader().valueEquals("Pragma", "no-cache")
//                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
//    }

}
