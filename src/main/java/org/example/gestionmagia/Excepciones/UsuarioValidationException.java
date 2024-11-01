package org.example.gestionmagia.Excepciones;

public class UsuarioValidationException extends RuntimeException {
    public UsuarioValidationException(String message) {
        super(message);
    }
}