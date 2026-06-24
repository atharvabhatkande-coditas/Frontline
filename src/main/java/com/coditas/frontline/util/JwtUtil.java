package com.coditas.frontline.util;

import com.coditas.frontline.dto.response.LoginResponseTokens;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.enums.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

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


    public LoginResponseTokens generateTokens(Users user,String roleType) {
        String accessToken=generateJwtToken(user.getUsername(),roleType,user.getRole());
        String refreshToken=generateRefreshToken();

        return LoginResponseTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateJwtToken(String username, String roleType, RoleType role){
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("TaskManagementApp")
                .setIssuedAt(new Date())
                .claim("roles",role)
                .claim("role-type",roleType)
                .setExpiration(new Date(System.currentTimeMillis()+accessExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    private String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }



}
