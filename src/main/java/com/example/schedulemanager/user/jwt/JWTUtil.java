package com.example.schedulemanager.user.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public String getid(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);
    }


    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }


    public String createJwt(String id, Long expiredMs) {
        return Jwts.builder()
                .claim("id", id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

}

