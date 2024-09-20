package com.empiricus.service_usuario.domain.exception;

public class UsuarioExistException extends  RuntimeException{
    public UsuarioExistException(String message) {
        super(message);
    }
}
