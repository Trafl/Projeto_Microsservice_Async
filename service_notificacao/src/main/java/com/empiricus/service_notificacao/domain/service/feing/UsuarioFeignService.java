package com.empiricus.service_notificacao.domain.service.feing;

import com.empiricus.service_notificacao.domain.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name ="service_usuario")
public interface UsuarioFeignService {

    @GetMapping("/api/usuario/{usuarioId}")
    Usuario getOneUsuario(@PathVariable Long usuarioId);

    @GetMapping("/api/usuario/{usuarioId}/exist")
    Boolean usuarioExist(@PathVariable Long usuarioId);

    @GetMapping( "/api/usuario/admins")
    List<String> getAdmins();
}

