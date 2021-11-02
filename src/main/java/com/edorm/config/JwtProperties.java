package com.edorm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public class JwtProperties {

    public static final String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * JWT expiration time in minutes.
     */
    private long expirationTime;

    public long getExpirationTime() {
        return expirationTime * 60 * 1000;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

}