package com.returntaxi.returntaxibe.security;

import com.returntaxi.returntaxibe.user.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private long expiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private Algorithm getAlgorithm() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return Algorithm.HMAC256(decodedKey);
    }

    public String generateToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("ROLE_USER");

        var builder = JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration));

        if (userDetails instanceof User customUser) {
            builder.withClaim("userId", customUser.getUserId());
        }

        return builder.sign(getAlgorithm());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("ROLE_USER");

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration))
                .sign(getAlgorithm());
    }

    public String extractUsername(String token) {
        return decode(token).getSubject();
    }

    public String extractRole(String token) {
        return decode(token).getClaim("role").asString();
    }

    public DecodedJWT decode(String token) {
        return JWT.require(getAlgorithm())
                .build()
                .verify(token);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String tokenEmail = extractUsername(token);
        return tokenEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return decode(token).getExpiresAt().before(new Date());
    }
}
