package org.fillUsIn.exception;

public class EmailExistsException extends RuntimeException {
  public EmailExistsException(String message) {
    super(message);
  }
}
