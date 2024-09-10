package com.empiricus.service_notificacao.core.feing;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDecodeConfig {
    @Bean
    ErrorDecoder decoder (){
        return new CustomErrorDecoder();
    }
}
