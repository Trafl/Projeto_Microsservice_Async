package com.empiricus.service_gateway.api.controller;

import com.empiricus.service_gateway.core.jwt.JwtProvider;
import com.empiricus.service_gateway.domain.model.JwtResponse;
import com.empiricus.service_gateway.domain.model.Login;
import com.empiricus.service_gateway.domain.service.webclient.UsuarioServiceWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioServiceWebClient webClient;

    private final JwtProvider jwtProvider;

    @PostMapping()
    public Mono<ResponseEntity<JwtResponse>> login(@RequestBody Login login) {
        return webClient.authenticateUsuario(login)
                .map(usuario -> {
                    String token = jwtProvider.createToken(usuario.getNome(), usuario.getEhAdmin());
                    return ResponseEntity.ok(new JwtResponse(token));
                })
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }
}
