package com.empiricus.service_email.domain.service.openfeign;

import com.empiricus.service_email.domain.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="SERVICE-USUARIO")
public interface UsuarioFeignService {

    @GetMapping("/api/usuario/{usuarioId}")
    ResponseEntity<Usuario> getUsuario(@PathVariable Long usuarioId);

}
