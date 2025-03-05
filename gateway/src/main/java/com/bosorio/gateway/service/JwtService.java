package com.bosorio.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String JWT_SECRET;

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    private Mono<Claims> getClaims(String token) {
        return Mono.fromCallable(() ->
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
        );
    }

    public Mono<Boolean> validateToken(String token) {
        return getClaims(token)
                .map(claims -> !claims.getExpiration().before(new Date()))
                .onErrorResume(e -> Mono.just(false));
    }

    public Mono<String> getUserId(String token) {
        return getClaims(token).map(Claims::getSubject);
    }

    public Mono<String> getRole(String token) {
        return getClaims(token).map(claims ->
                claims.get("role", String.class)
        );
    }

}
