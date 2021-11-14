package com.edorm.config;

import com.edorm.auth.*;
import com.edorm.controllers.RestEndpoint;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Key;
import java.util.UUID;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String UNIVERSAL_MATCHER = "/**";
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProperties jwtProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())

                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtProperties, jwtSecret()))
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtSecret()), UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()

                .antMatchers(RestEndpoint.SWAGGER_UI + UNIVERSAL_MATCHER).permitAll()
                .antMatchers(RestEndpoint.SWAGGER_DOCUMENTATION + UNIVERSAL_MATCHER).permitAll()
                .antMatchers(RestEndpoint.SWAGGER_RESOURCES + UNIVERSAL_MATCHER).permitAll()

                .antMatchers(RestEndpoint.LOGIN).permitAll()

                .antMatchers(RestEndpoint.REGISTRATION + UNIVERSAL_MATCHER).authenticated()

                .antMatchers(RestEndpoint.ROOM + UNIVERSAL_MATCHER).authenticated()
                .antMatchers(RestEndpoint.COMPOSITION + UNIVERSAL_MATCHER).authenticated()

                .antMatchers(RestEndpoint.POST + UNIVERSAL_MATCHER).authenticated()

                .antMatchers(RestEndpoint.RENTABLE_ITEM + UNIVERSAL_MATCHER).authenticated()
                .antMatchers(RestEndpoint.RENT_HISTORY + UNIVERSAL_MATCHER).authenticated()

                .anyRequest().authenticated()

                .and()
                .headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Key jwtSecret() {
        String secret = UUID.randomUUID().toString() + UUID.randomUUID();
        byte[] keyBytes = Decoders.BASE64.decode(secret.replaceAll("-", "x"));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping(UNIVERSAL_MATCHER).allowedMethods("*");
            }
        };
    }


}