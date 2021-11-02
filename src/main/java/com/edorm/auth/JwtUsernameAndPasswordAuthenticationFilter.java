package com.edorm.auth;

import com.edorm.config.JwtProperties;
import com.edorm.models.users.UserCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final JwtProperties jwtProperties;
    private final Key jwtSecret;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtProperties jwtProperties, Key jwtSecret) {
        this.authManager = authManager;
        this.jwtProperties = jwtProperties;
        this.jwtSecret = jwtSecret;

        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getUsername(), creds.getPassword(), Collections.emptyList());

            return authManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) {
        long now = System.currentTimeMillis();

        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + (jwtProperties.getExpirationTime())))
                .signWith(jwtSecret)
                .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        /*response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.getWriter().write("Bearer " + token);
        response.getWriter().flush();*/
    }

}