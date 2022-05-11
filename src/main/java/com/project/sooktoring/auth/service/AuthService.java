package com.project.sooktoring.auth.service;

import com.project.sooktoring.auth.dto.TokenRequest;
import com.project.sooktoring.auth.dto.TokenResponse;
import com.project.sooktoring.auth.exception.ExpiredRefreshTokenException;
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

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    public TokenResponse refresh(TokenRequest tokenRequest) {
        AuthToken appToken = authTokenProvider.convertAuthToken(tokenRequest.getAppToken());
        AuthToken refreshToken = authTokenProvider.convertAuthToken(tokenRequest.getRefreshToken());

        //Refresh Token 검증
        if(refreshToken.validateToken()) {
            //App Token에서 providerId get
            String providerId = appToken.getTokenClaims().getSubject();

            //DB에서 providerId 기반으로 Refresh Token 값 get
            RefreshToken dbRefreshToken = refreshTokenRepository.findByKey(providerId)
                    .orElseThrow(() -> new ExpiredRefreshTokenException("Expired Refresh Token"));
            //Refresh Token 일치 검사
            if (!dbRefreshToken.getValue().equals(refreshToken.getToken())) {
                throw new ExpiredRefreshTokenException("Invalid Refresh Token");
            }

            //새로운 토큰 생성
            AuthToken newAppToken = authTokenProvider.createUserAppToken(providerId);
            AuthToken newRefreshToken = authTokenProvider.createUserRefreshToken();
            //DB Refresh Token 업데이트
            dbRefreshToken.updateToken(newRefreshToken.getToken());

            return TokenResponse.builder()
                    .appToken(newAppToken.getToken())
                    .refreshToken(newRefreshToken.getToken())
                    .build();
        }

        return null;
    }
}
