package com.example.quiz.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    public JwtUtil() {
        System.out.println("==============================JwtUtil bean created");
    }

    // convert secret string to Key
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // generate token
    public String generateToken(String email, String role) { // add role param
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // store role in token
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractRole(String token) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role"); // read role from token
    }

    // extract email from token
    public String extractEmail(String token) {
        return Jwts.parserBuilder() // parserBuilder() not parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // validate token
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder() // parserBuilder() not parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}