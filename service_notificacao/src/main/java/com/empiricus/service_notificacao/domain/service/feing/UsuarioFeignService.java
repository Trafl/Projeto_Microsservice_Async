package com.empiricus.service_notificacao.domain.service.feing;

import com.empiricus.service_notificacao.domain.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.CompletableFuture;

@FeignClient(name ="service-usuario")
public interface UsuarioFeignService {

    @GetMapping("/api/usuario/{usuarioId}")
    Usuario getOneUsuario(@PathVariable Long usuarioId);

}

