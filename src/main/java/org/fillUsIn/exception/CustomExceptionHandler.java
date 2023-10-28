package org.fillUsIn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<Object> handleUsernameExists(UsernameExistsException ex, WebRequest request) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("timestamp", LocalDateTime.now());
      body.put("message", ex.getMessage());
      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Object> handleEmailExists(EmailExistsException ex, WebRequest request) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("timestamp", LocalDateTime.now());
      body.put("message", ex.getMessage());
      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}


