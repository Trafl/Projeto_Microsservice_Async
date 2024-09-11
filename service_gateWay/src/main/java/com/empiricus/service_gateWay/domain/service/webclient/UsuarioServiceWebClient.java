package com.empiricus.service_gateway.domain.service.webclient;

import com.empiricus.service_gateway.domain.model.Login;
import com.empiricus.service_gateway.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UsuarioServiceWebClient {

    private  WebClient webClient;

    public UsuarioServiceWebClient (WebClient.Builder builder){
        this.webClient = builder.baseUrl("http://service-usuario").build();
    }

    public Mono<Usuario> authenticateUsuario(Login login){
        return webClient.post()
                .uri("/autenticacao")
                .bodyValue(login)
                .retrieve()
                .bodyToMono(Usuario.class);
    }
}
