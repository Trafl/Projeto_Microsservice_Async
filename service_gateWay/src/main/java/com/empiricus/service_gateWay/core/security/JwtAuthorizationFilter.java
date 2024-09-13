//package com.empiricus.service_gateway.core.security;
//
//import com.empiricus.service_gateway.core.jwt.JwtProvider;
//import io.jsonwebtoken.Claims;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.util.Collections;
//
//@Log4j2
////@Component
////@RequiredArgsConstructor
//public class JwtAuthorizationFilter implements WebFilter {
//
//    private final JwtProvider jwtTokenProvider;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//
//        if (exchange.getRequest().getPath().toString().equals("/login")) {
//            return chain.filter(exchange);
//        }
//
//        String token = resolveToken(exchange);
//
//        if (token != null) {
//            log.info("Token encontrado, validando...");
//            if (jwtTokenProvider.validateToken(token)) {
//                log.info("Token validado com sucesso.");
//                Claims claims = jwtTokenProvider.getClaims(token);
//                Boolean isAdmin = claims.get("admin", Boolean.class);
//
//                log.info("Claims: " + claims.toString());
//                log.info("Campo 'admin' extraído: " + isAdmin);
//
//                if (isAdmin) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            claims.getSubject(), null, Collections.emptyList()
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    log.info("Usuário é administrador, permitindo acesso.");
//                    return chain.filter(exchange);
//                } else {
//                    log.warn("Usuário não é administrador, acesso negado.");
//                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                    return exchange.getResponse().setComplete();
//                }
//            } else {
//                log.warn("Token inválido.");
//            }
//        } else {
//            log.warn("Token não encontrado.");
//        }
//
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        return exchange.getResponse().setComplete();
//    }
//
//    private String resolveToken(ServerWebExchange exchange) {
//        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
