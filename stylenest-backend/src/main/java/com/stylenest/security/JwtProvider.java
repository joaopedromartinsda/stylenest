package com.stylenest.security;

import com.stylenest.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

    private final SecretKey key;
    private final long expiration;

    public JwtProvider(@Value("${security.jwt.secret}") String secret,
                       @Value("${security.jwt.expiration:3600000}") long expiration) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expiration = expiration;
    }

    public String generateToken(Usuario usuario) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
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
