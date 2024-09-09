package com.empiricus.service_email.core.feing;

import com.empiricus.service_email.domain.exception.UsuarioBadRequestException;
import com.empiricus.service_email.domain.exception.UsuarioInteralServerErrorException;
import com.empiricus.service_email.domain.exception.UsuarioNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        switch (status){
            case NOT_FOUND:
                return new UsuarioNotFoundException("Usuario não Registrado");
            case BAD_REQUEST:
                return new UsuarioBadRequestException("Chamada incorreta ao serviço de usuario");
            case INTERNAL_SERVER_ERROR:
                return new UsuarioInteralServerErrorException("Error interno no serviço de Usuario");
            default:
                return new Exception("Erro generico " + s + " Status: " + status );
        }
    }
}
