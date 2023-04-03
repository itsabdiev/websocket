package com.abdiev.application.service;


import com.abdiev.application.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
@Slf4j
public class JwtService {
    //TODO: add refresh token repo and logic
    @Value("${security.json.token.secret}")
    private String secretKey;
    private  final UserService userService;

    public JwtService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(String username) {
        Instant issuedTime = Instant.now();
        Instant validity = issuedTime.plus(Duration.of(10, ChronoUnit.MINUTES));
        return JWT
                .create()
                .withSubject(username)
                .withIssuedAt(issuedTime)
                .withExpiresAt(validity)  //TODO: -> add issuerParty & claims
                .sign(Algorithm.HMAC256(secretKey));
    }
    public Authentication validateToken(String token) {
        User user = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT decoded = verifier.verify(token);
            user = (User) userService.loadUserByUsername(decoded.getSubject());
        } catch (IllegalArgumentException | JWTVerificationException | UsernameNotFoundException e) {
            log.error("JWT exception {}",e.getMessage());
        }
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());
        }
        return null;
    }
}
