package com.example.beehiveproject.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

//The security of the token is entirely the user's responsibility.
@Component
public class JwtUtil {

    //Take these values from application.properties
    private final Key key;
    private final long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    //This part is where we generate our token. Defining id as a subject of token.
    //This subject is like an identifier for token.
    //And declaring custom claims such as role.
    public String generateToken(String id, String role) {
        return Jwts.builder()
                .setSubject(id)
                .claim("role", role)
                .setIssuedAt(new Date())//Created at date
                .setExpiration(new Date(System.currentTimeMillis() + expiration))//Adding exp time to now
                .signWith(key, SignatureAlgorithm.HS256)//Algorithm of the şifreleme
                .compact();//Finally, compact it into a string
    }

    // Extracts the user ID from the token
    public String extractId(String token) {
        return getClaims(token).getSubject();
    }

    // Extracts the user role from the token
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Validates the token. If the signature is valid and the token is not expired, it returns true.
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);// if parsing succeeds, token is valid
            return true;
        } catch (JwtException e) {
            return false;// invalid signature or expired token
        }
    }

    // This helper method parses the token and returns the Claims (payload).
    // Claims include both standard (e.g. subject, expiration) and custom (e.g. role) fields.
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)// provide the same key used to sign the token
                .build()
                .parseClaimsJws(token)// validate and parse the token
                .getBody();// return the payload (claims)
    }
}
