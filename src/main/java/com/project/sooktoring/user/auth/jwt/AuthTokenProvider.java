package com.project.sooktoring.user.auth.jwt;

import com.project.sooktoring.user.auth.exception.ExpiredAccessTokenException;
import com.project.sooktoring.user.auth.util.UserPrincipal;
import com.project.sooktoring.user.auth.enumerate.Role;
import com.project.sooktoring.user.auth.domain.User;
import com.project.sooktoring.user.auth.repository.UserRepository;
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
import java.util.Optional;

@Slf4j
@Component
public class AuthTokenProvider {

   @Value("${app.auth.accessTokenExpiry}")
   private String accessTokenExpiry;

   @Value("${app.auth.refreshTokenExpiry}")
   private String refreshTokenExpiry;

   private final Key key;
   private final UserRepository userRepository;

   public AuthTokenProvider(@Value("${app.auth.tokenSecret}") String secretKey, UserRepository userRepository) {
       this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
       this.userRepository = userRepository;
   }

    // USER에 대한 AccessToken 생성
   public AuthToken createAccessToken(String providerId, Long userId) {
       Date expiryDate = getExpiryDate(accessTokenExpiry);
       return new AuthToken(providerId, userId, expiryDate, key);
   }

   public AuthToken createRefreshToken() {
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
       Long userId = claims.get("userId", Long.class);
       Optional<User> userOptional = userRepository.findById(userId);
       if (userOptional.isEmpty()) {
          throw new ExpiredAccessTokenException(null, claims, "탈퇴한 이용자입니다.");
       }

       Role role = userOptional.get().getRole();
       Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));

       UserPrincipal principal = UserPrincipal.create(userId, claims.getSubject(), authorities);
       return new UsernamePasswordAuthenticationToken(principal, "", authorities);
   }
}
