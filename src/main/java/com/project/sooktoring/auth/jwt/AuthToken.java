package com.project.sooktoring.auth.jwt;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Getter
@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    private final String token;
    private final Key key; //토큰 생성시 사용할 비밀키

    AuthToken(String providerId, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(providerId, expiry);
    }

    private String createAuthToken(String providerId, Date expiry) {
        return Jwts.builder()
                .setSubject(providerId)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }
}
