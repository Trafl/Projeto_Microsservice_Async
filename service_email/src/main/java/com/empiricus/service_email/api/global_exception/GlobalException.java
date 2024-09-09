package com.empiricus.service_email.api.global_exception;

import com.empiricus.service_email.domain.exception.EmailNotFoundException;
import com.empiricus.service_email.domain.exception.UsuarioBadRequestException;
import com.empiricus.service_email.domain.exception.UsuarioInteralServerErrorException;
import com.empiricus.service_email.domain.exception.UsuarioNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
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

@Log4j2
@Configuration
@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

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

        log.error("[{}] - [GlobalException] - MethodArgumentNotValidException: Erro ao validar campos", LocalDateTime.now());
        errors.forEach((fieldName, message) -> {
            log.error("[{}] - [GlobalException] - Campo invalido: {} - Mensagem: {}", LocalDateTime.now(), fieldName, message);
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

    @ExceptionHandler(EmailNotFoundException.class)
    ProblemDetail handlerEmailNotFoundException(EmailNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problem.setTitle("Email não registrado");
        problem.setProperty("timestamp", Instant.now());

        log.error("[{}] - [GlobalExeption] - EmailNotFoundException: {}", LocalDateTime.now(), e.getMessage());
        return problem;

    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    ProblemDetail handlerUsuarioNotFoundException(UsuarioNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problem.setTitle("Usuario não registrado");
        problem.setProperty("timestamp", Instant.now());

        log.error("[{}] - [GlobalExeption] - UsuarioNotFoundException: {}", LocalDateTime.now(), e.getMessage());
        return problem;

    }

    @ExceptionHandler(UsuarioBadRequestException.class)
    ProblemDetail handlerUsuarioBadRequestException(UsuarioBadRequestException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        problem.setTitle("Chamada incorreta ao serviço de usuario");
        problem.setProperty("timestamp", Instant.now());

        log.error("[{}] - [GlobalExeption] - UsuarioBadRequestException: {}", LocalDateTime.now(), e.getMessage());
        return problem;

    }

    @ExceptionHandler(UsuarioInteralServerErrorException.class)
    ProblemDetail handlerUsuarioInteralServerErrorException(UsuarioInteralServerErrorException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        problem.setTitle("Error interno no serviço de Usuario");
        problem.setProperty("timestamp", Instant.now());

        log.error("[{}] - [GlobalExeption] - UsuarioInteralServerErrorException: {}", LocalDateTime.now(), e.getMessage());
        return problem;

    }

}
