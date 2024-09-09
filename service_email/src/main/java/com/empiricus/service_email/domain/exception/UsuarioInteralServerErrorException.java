package com.empiricus.service_email.domain.exception;

public class UsuarioInteralServerErrorException extends  RuntimeException {
    public UsuarioInteralServerErrorException(String message) {
        super(message);
    }
}
