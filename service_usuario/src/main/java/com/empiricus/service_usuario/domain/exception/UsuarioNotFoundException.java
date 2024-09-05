package com.empiricus.service_usuario.domain.exception;

public class UsuarioNotFoundException extends  RuntimeException{
    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
