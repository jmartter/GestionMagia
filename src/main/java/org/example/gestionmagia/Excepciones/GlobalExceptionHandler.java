// src/main/java/org/example/gestionmagia/Excepciones/GlobalExceptionHandler.java
package org.example.gestionmagia.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioValidationException.class)
    public ResponseEntity<String> handleUsuarioValidationException(UsuarioValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}