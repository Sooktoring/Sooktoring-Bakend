package com.project.sooktoring.auth.service;

import com.project.sooktoring.auth.dto.AuthResponse;
import com.project.sooktoring.auth.jwt.AuthToken;
import com.project.sooktoring.auth.jwt.AuthTokenProvider;
import com.project.sooktoring.domain.User;
import com.project.sooktoring.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;

    public AuthResponse updateToken(AuthToken authToken) {
        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }

        String providerId = claims.getSubject();
        AuthToken newAppToken = authTokenProvider.createUserAppToken(providerId);

        return AuthResponse.builder()
                .appToken(newAppToken.getToken())
                .build();
    }

    public Long getUserId(String token) {
        AuthToken authToken = authTokenProvider.convertAuthToken(token);
        Claims claims = authToken.getTokenClaims();

        if (claims == null) {
            return null;
        }

        Optional<User> user = userRepository.findByProviderId(claims.getSubject());
        return user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.")).getId();
    }
}
