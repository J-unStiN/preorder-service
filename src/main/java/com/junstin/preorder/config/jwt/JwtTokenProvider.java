package com.junstin.preorder.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junstin.preorder.config.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.access-key}")
    private String accessSecretKey;
    @Value("${jwt.refresh-key}")
    private String refreshSecretKey;
    @Value("${jwt.access-expired}")
    private long expirationMinutes;
    @Value("${jwt.refresh-expired}")
    private long refreshExpirationMinutes;
    private final RedisService redisService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtTokenProvider(RedisService redisService) {
        this.redisService = redisService;
    }

    public String createAccessToken(String userSpecification) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, accessSecretKey.getBytes())
                .setSubject(userSpecification)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES)))
                .compact();
    }

    public String createRefreshToken() {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey.getBytes())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationMinutes, ChronoUnit.MINUTES)))
                .compact();
    }

    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        String subject = decodeJwtPayloadSubject(oldAccessToken);
        return createAccessToken(subject);
    }
    public Claims validateRefreshToken(String refreshToken) {
        Key key = Keys.hmacShaKeyFor(refreshSecretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    public String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]),
                        StandardCharsets.UTF_8),
                        Map.class)
                .get("sub").toString();
    }

    private Jws<Claims> validateAndParseToken(String refreshToken) {
        return Jwts.parser()
                .setSigningKey(accessSecretKey.getBytes())
                .parseClaimsJws(refreshToken);
    }

    public String validateTokenAndGetSubject(String token) {
        return validateAndParseToken(token).getBody().getSubject();
    }
}
