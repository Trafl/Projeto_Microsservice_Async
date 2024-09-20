package com.empiricus.service_usuario.api.controller_exception;

import com.empiricus.service_usuario.domain.exception.UsuarioExistException;
import com.empiricus.service_usuario.domain.exception.UsuarioNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GlobalException extends ResponseEntityExceptionHandler {

    private String logTime = LocalDateTime.now().toString();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatusCode.valueOf(400));
        problem.setTitle("Erro ao validar os campos fornecidos");
        problem.setDetail("Um ou mais campos estão invalidos. corrija e tente novamente.");
        problem.setProperty("timestamp", Instant.now());

        Map<String, String> errors = getErrorFields(ex);
        errors.forEach((fieldName, message) -> {
            problem.setProperty(fieldName, message);
        });

        log.error("[{}] - [GlobalException] - MethodArgumentNotValidException: Erro ao validar campos", logTime);
        errors.forEach((fieldName, message) -> {
            log.error("[{}] - [GlobalException] - Campo invalido: {} - Mensagem: {}", logTime, fieldName, message);
        });

        return new ResponseEntity<Object>(problem, status);
    }

    private Map<String, String> getErrorFields(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    ProblemDetail handlerUsuarioNotFoundException(UsuarioNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problem.setTitle("Usuario não registrado");
        problem.setProperty("timestamp", Instant.now());

        log.error("[{}] - [GlobalExeption] - UsuarioNotFoundException: {}", logTime, e.getMessage());
        return problem;

    }

    @ExceptionHandler(UsuarioExistException.class)
    ProblemDetail handlerUsuarioExistException(UsuarioExistException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        problem.setTitle("Nome de usuario já registrado");
        problem.setProperty("timestamp", Instant.now());

        log.error("[{}] - [GlobalExeption] - UsuarioExistException: {}", logTime, e.getMessage());
        return problem;

    }

}
