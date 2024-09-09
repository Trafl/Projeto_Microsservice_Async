package com.empiricus.service_email.domain.service.openfeign;

import com.empiricus.service_email.core.feing.ErrorDecoderConfig;
import com.empiricus.service_email.domain.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="service-usuario", configuration = ErrorDecoderConfig.class)
public interface UsuarioFeignService {

    @GetMapping("/api/usuario/{usuarioId}")
    Usuario getUsuario(@PathVariable Long usuarioId);
}
