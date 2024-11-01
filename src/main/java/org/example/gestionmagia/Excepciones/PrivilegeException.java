package org.example.gestionmagia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PrivilegeException extends RuntimeException {
    public PrivilegeException(String message) {
        super(message);
    }
}