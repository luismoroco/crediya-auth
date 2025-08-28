package com.crediya.auth.logger;

import com.crediya.common.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Slf4jLogger implements Logger {

  private static final org.slf4j.Logger logger =  LoggerFactory.getLogger(Slf4jLogger.class);

  @Override
  public void trace(String message, Object ...args) {
    logger.trace(message, args);
  }

  @Override
  public void info(String message, Object ...args) {
    logger.info(message, args);
  }

  @Override
  public void warn(String message, Object ...args) {
    logger.warn(message, args);
  }

  @Override
  public void error(String message, Object ...args) {
    logger.error(message, args);
  }
}
