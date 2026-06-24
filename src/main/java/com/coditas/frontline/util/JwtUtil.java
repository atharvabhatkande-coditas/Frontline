package com.coditas.frontline.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final long accessExpiration;
    private final SecretKey key;

    public JwtUtil(@Value("${jwt.accessExpiration}")long accessExpiration ,@Value("${jwt.secret}") String secret){
        key= Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpiration=accessExpiration;
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }
    public String extractRoleType(String token){
        return extractClaims(token).get("role-type",String.class);
    }

    public boolean validateToken(UserDetails userDetails, String username, String token){
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    public boolean isExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }



}
