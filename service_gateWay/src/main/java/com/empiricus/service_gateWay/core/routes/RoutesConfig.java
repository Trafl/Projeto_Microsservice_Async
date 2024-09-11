package com.empiricus.service_gateway.core.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("service-usuario", r -> r.path("/api/usuario/**")
                        .uri("lb://service-usuario"))
                .route("service-email", r -> r.path("/api/email/**")
                        .uri("lb://service-email"))
                .build();
    }
}
