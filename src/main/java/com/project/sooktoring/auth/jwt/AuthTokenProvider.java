package com.project.sooktoring.auth.jwt;

import com.project.sooktoring.auth.user.UserPrincipal;
import com.project.sooktoring.auth.exception.InvalidGoogleAccessTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class AuthTokenProvider {

   @Value("${app.auth.appTokenExpiry}")
   private String appTokenExpiry;

   @Value("${app.auth.refreshTokenExpiry}")
   private String refreshTokenExpiry;

   private final Key key;

   public AuthTokenProvider(@Value("${app.auth.tokenSecret}") String secretKey) {
       this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
   }

    // USER에 대한 AccessToken(AppToken) 생성
   public AuthToken createUserAppToken(String providerId) {
       Date expiryDate = getExpiryDate(appTokenExpiry);
       return new AuthToken(providerId, expiryDate, key);
   }

   public AuthToken createUserRefreshToken() {
       Date expiryDate = getExpiryDate(refreshTokenExpiry);
       return new AuthToken(expiryDate, key);
   }

    private static Date getExpiryDate(String expiry) {
        return new Date(System.currentTimeMillis() + Long.parseLong(expiry));
    }

   public AuthToken convertAuthToken(String token) {
       return new AuthToken(token, key);
   }

   public Authentication getAuthentication(AuthToken authToken) {
       Claims claims = authToken.getTokenClaims();
       Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

       UserPrincipal principal = UserPrincipal.create(claims.getSubject(), "", authorities);
       return new UsernamePasswordAuthenticationToken(principal, "", authorities);
   }
}
