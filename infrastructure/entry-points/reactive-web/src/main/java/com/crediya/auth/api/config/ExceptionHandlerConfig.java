package com.crediya.auth.api.config;

import com.crediya.common.api.handling.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ GlobalExceptionHandler.class })
public class ExceptionHandlerConfig {
}
