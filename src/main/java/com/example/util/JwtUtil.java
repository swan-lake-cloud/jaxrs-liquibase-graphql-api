package com.example.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

public class JwtUtil {
    private static final String SECRET;
    private static final Key KEY;

    static {
        Properties props = new Properties();
        try (InputStream input = JwtUtil.class.getClassLoader().getResourceAsStream("application-local.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Could not load application-local.properties", e);
        }
        SECRET = props.getProperty("jwt.secret");
        KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public static String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
            .signWith(KEY, SignatureAlgorithm.HS512)
            .compact();
    }

    public static String validateToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(KEY)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}
