package com.project.sooktoring.auth.jwt;

import com.project.sooktoring.auth.exception.ExpiredAppTokenException;
import com.project.sooktoring.auth.exception.ExpiredRefreshTokenException;
import io.jsonwebtoken.*;
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

    AuthToken(Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(null, expiry);
    }

    private String createAuthToken(String providerId, Date expiry) {
        return Jwts.builder()
                .setSubject(providerId)
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .compact();
    }

    public boolean validateToken() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            if (e.getClaims().getSubject() != null) {
                throw new ExpiredAppTokenException(e.getHeader(), e.getClaims(), "Expired App Token");
            } else {
                throw new ExpiredRefreshTokenException(e.getHeader(), e.getClaims(), "Expired Refresh Token");
            }
        }
        return true;
    }

    //만료된 토큰이어도 Claims 꺼내기 위해
    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
