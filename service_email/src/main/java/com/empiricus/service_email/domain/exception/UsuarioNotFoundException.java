package com.empiricus.service_email.domain.exception;

public class UsuarioNotFoundException extends  RuntimeException {
    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
