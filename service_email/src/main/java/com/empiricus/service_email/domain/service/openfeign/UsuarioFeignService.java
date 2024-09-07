package com.empiricus.service_email.domain.service.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="SERVICE-USUARIO")
public interface UsuarioFeignService {

    @GetMapping("/api/usuario/{usuarioId}/exist")
    boolean usuarioExist(@PathVariable Long usuarioId);

}
