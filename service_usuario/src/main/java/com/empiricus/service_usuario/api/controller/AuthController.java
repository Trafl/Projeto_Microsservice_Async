package com.empiricus.service_usuario.api.controller;

import com.empiricus.service_usuario.domain.model.LoginRequest;
import com.empiricus.service_usuario.domain.model.UsuarioResponse;
import com.empiricus.service_usuario.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository repository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request){
        var usuarioOptional = repository.findByNomeAndPassword(request.getUsername());

        if(usuarioOptional.isPresent()){
            var usuario = usuarioOptional.get();
            if(passwordEncoder.matches(request.getPassword(), usuario.getPassword())){
                UsuarioResponse response = new UsuarioResponse();
                response.setNome(usuario.getNome());
                response.setEhAdmin(usuario.getEh_admin());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
