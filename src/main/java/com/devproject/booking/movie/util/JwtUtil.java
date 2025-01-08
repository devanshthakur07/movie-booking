package com.devproject.booking.movie.util;

import com.devproject.booking.movie.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; //1hour

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, String role) {
        System.out.println("Generating token...");
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Invalid or expired token");
        }
    }

    public String extractUsername(String token) {
        try {
            return extractClaims(token).getSubject();
        }
        catch(ExpiredJwtException ex) {
            throw new TokenExpiredException("Token expired");
        }
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        System.out.println("token validation in progress...");
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }


}
