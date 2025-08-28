package com.crediya.auth.usecase.user;

public enum DomainError {
  ENTITY_NOT_FOUND {
    @Override
    public String get(String entityName) {
      return String.format("%s not found", entityName);
    }

    @Override
    public String get(String entityName, Object value) {
      return String.format("%s %s not found", value, entityName);
    }
  },
  INVALID_PARAMETER {
    @Override
    public String get(String entityName) {
      return String.format("Invalid parameter: %s", entityName);
    }

    @Override
    public String get(String entityName, Object value) {
      return String.format("%s %s invalid parameter", value, entityName);
    }
  },
  ENTITY_ALREADY_EXISTS {
    @Override
    public String get(String entityName) {
      return String.format("%s already exists", entityName);
    }

    @Override
    public String get(String entityName, Object value) {
      return String.format("%s %s already exists", value, entityName);
    }
  };

  public abstract String get(String entityName);

  public abstract String get(String entityName, Object value);
}
