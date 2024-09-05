package com.empiricus.service_email.domain.exception;

public class UsuarioOrEmailNotFound extends  RuntimeException {
    public UsuarioOrEmailNotFound(String message) {
        super(message);
    }
}
