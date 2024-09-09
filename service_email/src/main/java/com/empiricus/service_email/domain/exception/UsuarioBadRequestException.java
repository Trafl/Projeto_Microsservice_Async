package com.empiricus.service_email.domain.exception;

public class UsuarioBadRequestException extends  RuntimeException {
    public UsuarioBadRequestException(String message) {
        super(message);
    }
}
