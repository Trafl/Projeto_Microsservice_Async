package com.empiricus.service_usuario.core.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigMapper {
    @Bean
    ModelMapper config(){
        return new ModelMapper();
    }
}
