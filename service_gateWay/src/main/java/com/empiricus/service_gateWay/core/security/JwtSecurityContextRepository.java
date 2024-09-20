package com.empiricus.service_gateway.core.security;

import com.empiricus.service_gateway.core.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtProvider jwtProvider;


    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        var token = resolverToken(exchange);
        if(token != null && jwtProvider.validateToken(token)){
            var claims = jwtProvider.getClaims(token);
            var auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, Collections.emptyList()
            );
            return Mono.just(new SecurityContextImpl(auth));
        }
        return Mono.empty();
    }

    private String resolverToken(ServerWebExchange exchange){
        var bearToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(bearToken != null && bearToken.startsWith("Bearer ")){
            return bearToken.substring(7);
        }
        return null;
    }
}
