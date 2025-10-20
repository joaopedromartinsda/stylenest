package com.stylenest.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;
import javax.crypto.SecretKey;

@Service
public class JwtProvider {

    // Chave secreta (deve ter pelo menos 64 bytes para HS512)
    private static final String JWT_SECRET = "sua-chave-super-secreta-de-64-bytes-ou-mais-para-HS512-segura";
    
    // Tempo de expiração do token (ex: 1 hora)
    private static final long EXPIRATION = 3600000;

    private final SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

    public String generateToken(com.stylenest.model.Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // ou ID, se preferir
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}