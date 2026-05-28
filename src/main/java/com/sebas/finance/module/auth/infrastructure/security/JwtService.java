package com.sebas.finance.module.auth.infrastructure.security;

import com.sebas.finance.module.auth.domain.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

@Component
public class JwtService {

    private final SecretKey signingKey;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtService(
            @Value("${auth.jwt.secret}") String secret,
            @Value("${auth.jwt.access-expiration-ms}") long accessExpiration,
            @Value("${auth.jwt.refresh-expiration-ms}") long refreshExpiration) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateAccessToken(String subject) {
        return buildToken(subject, accessExpiration, "access");
    }

    public String generateRefreshToken(String subject) {
        return buildToken(subject, refreshExpiration, "refresh");
    }

    private String buildToken(String subject, long expiration, String type) {
        Date now = new Date();
        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .claim("type", type)
                .signWith(signingKey)
                .compact();
    }

    public String extractSubject(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractTokenType(String token) {
        return extractClaims(token).get("type", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Token invalido o expirado");
        }
    }
}
