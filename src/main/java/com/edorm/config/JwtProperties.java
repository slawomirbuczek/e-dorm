package com.edorm.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@ConfigurationProperties("jwt")
public class JwtProperties {

    public static final String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * JWT expiration time in minutes.
     */
    private Key secret;

    /**
     * JWT expiration time in minutes.
     */
    private long expirationTime;

    public Key getSecret() {
        return secret;
    }

    public long getExpirationTime() {
        return expirationTime * 60 * 1000;
    }

    public void setSecret(String secret) {
        this.secret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

}