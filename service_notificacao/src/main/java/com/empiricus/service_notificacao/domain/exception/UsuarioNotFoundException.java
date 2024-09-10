package com.empiricus.service_notificacao.domain.exception;

public class UsuarioNotFoundException extends   RuntimeException{
    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
