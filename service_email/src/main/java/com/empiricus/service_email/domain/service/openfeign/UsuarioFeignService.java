package com.empiricus.service_email.domain.service.openfeign;

import com.empiricus.service_email.domain.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service_usuario")
public interface UsuarioFeignService {

    @GetMapping(value = "/api/usuario/{usuarioId}")
    Usuario getOneUsuario(@PathVariable Long usuarioId);

    @GetMapping(value = "/api/usuario/{usuarioId}/exist")
    Boolean usuarioExist(@PathVariable Long usuarioId);
}
