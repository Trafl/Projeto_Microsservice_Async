package com.empiricus.service_notificacao.core.feing;

import com.empiricus.service_notificacao.domain.exception.UsuarioNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {
            return new UsuarioNotFoundException("Usuário não encontrado");
        }

        return new Exception("Erro inesperado: " + response.status());
    }
}
