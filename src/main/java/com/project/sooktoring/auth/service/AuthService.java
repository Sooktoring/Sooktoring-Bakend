package com.project.sooktoring.auth.service;

import com.project.sooktoring.auth.dto.AuthResponse;
import com.project.sooktoring.auth.dto.TokenRequest;
import com.project.sooktoring.auth.jwt.AuthToken;
import com.project.sooktoring.auth.jwt.AuthTokenProvider;
import com.project.sooktoring.auth.jwt.RefreshToken;
import com.project.sooktoring.auth.jwt.RefreshTokenRepository;
import com.project.sooktoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    public AuthResponse refresh(TokenRequest tokenRequest) {
        //Refresh Token 검증
        AuthToken appToken = authTokenProvider.convertAuthToken(tokenRequest.getAppToken());
        AuthToken refreshToken = authTokenProvider.convertAuthToken(tokenRequest.getRefreshToken());

        if(!refreshToken.validateToken()) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        //App Token에서 providerId get
        String providerId = appToken.getTokenClaims().getSubject();

        //DB에서 providerId 기반으로 Refresh Token 값 get
        RefreshToken dbRefreshToken = refreshTokenRepository.findByKey(providerId)
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        //Refresh Token 일치 검사
        if (!dbRefreshToken.getValue().equals(refreshToken.getToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        //새로운 토큰 생성
        AuthToken newAppToken = authTokenProvider.createUserAppToken(providerId);
        AuthToken newRefreshToken = authTokenProvider.createUserRefreshToken(providerId);

        //DB Refresh Token 업데이트
        dbRefreshToken.updateToken(newRefreshToken.getToken());

        return AuthResponse.builder()
                .appToken(newAppToken.getToken())
                .refreshToken(newRefreshToken.getToken())
                .isNewUser(false)
                .build();
    }

//    public Long getUserId(String token) {
//        AuthToken authToken = authTokenProvider.convertAuthToken(token);
//        Claims claims = authToken.getTokenClaims();
//
//        if (claims == null) {
//            return null;
//        }
//
//        Optional<User> user = userRepository.findByProviderId(claims.getSubject());
//        return user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.")).getId();
//    }
}
