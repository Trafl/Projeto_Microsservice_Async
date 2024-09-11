package com.empiricus.service_gateway.core.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Log4j2
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpiration;

    public String createToken(String username, Boolean ehAdmin) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("admin", ehAdmin);

        LocalDateTime now = LocalDateTime.now();
        Date issueDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date expiryDate = new Date(issueDate.getTime() + jwtExpiration);

        log.info("Montou o token");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issueDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            log.info("Token validado");
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expirado", e);
        } catch (UnsupportedJwtException e) {
            log.error("Token não suportado", e);
        } catch (MalformedJwtException e) {
            log.error("Token malformado", e);
        } catch (SignatureException e) {
            log.error("Assinatura do token inválida", e);
        } catch (Exception e) {
            log.error("Erro ao validar o token", e);
        }
        return false;
    }

    public Claims getClaims(String token) {
        try {
            log.info("getClaims");
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("Token expirado ao tentar extrair claims", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("Token não suportado ao tentar extrair claims", e);
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Token malformado ao tentar extrair claims", e);
            throw e;
        } catch (SignatureException e) {
            log.error("Assinatura do token inválida ao tentar extrair claims", e);
            throw e;
        } catch (Exception e) {
            log.error("Erro ao extrair claims do token", e);
            throw e;
        }
    }
}
