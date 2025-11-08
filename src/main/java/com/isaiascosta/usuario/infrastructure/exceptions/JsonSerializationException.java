package com.isaiascosta.usuario.infrastructure.exceptions;

public class JsonSerializationException extends RuntimeException {
  public JsonSerializationException(String message) {
    super(message);
  }
}
