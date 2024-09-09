package com.empiricus.service_notificacao.domain.service.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@FeignClient(name ="service-email")
public interface EmailFeignService {

    @GetMapping("/api/email/admins")
    List<String> getAdmins();
}

