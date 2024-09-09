package com.empiricus.service_email.domain.exception;

public class EmailNotFoundException extends  RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
