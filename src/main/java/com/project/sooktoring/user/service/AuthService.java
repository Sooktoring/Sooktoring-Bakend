package com.project.sooktoring.user.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.user.dto.request.TokenRequest;
import com.project.sooktoring.user.dto.response.TokenResponse;
import com.project.sooktoring.user.jwt.AuthToken;
import com.project.sooktoring.user.jwt.AuthTokenProvider;
import com.project.sooktoring.user.domain.RefreshToken;
import com.project.sooktoring.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenResponse refresh(TokenRequest tokenRequest) {
        AuthToken accessToken = authTokenProvider.convertAuthToken(tokenRequest.getAccessToken());
        AuthToken refreshToken = authTokenProvider.convertAuthToken(tokenRequest.getRefreshToken());

        //Refresh Token 검증
        if(refreshToken.validateToken()) {
            //Access Token에서 providerId get
            String providerId = accessToken.getTokenClaims().getSubject();
            Long userId = accessToken.getTokenClaims().get("userId", Long.class);

            //DB에서 userId 기반으로 Refresh Token 값 get
            RefreshToken dbRefreshToken = refreshTokenRepository.findByKey(userId)
                    .orElseThrow(() -> new CustomException(INVALID_REFRESH_TOKEN));
            //Refresh Token 일치 검사
            if (!dbRefreshToken.getValue().equals(refreshToken.getToken())) {
                throw new CustomException(INVALID_REFRESH_TOKEN);
            }

            //새로운 토큰 생성
            AuthToken newAccessToken = authTokenProvider.createAccessToken(providerId, userId);
            AuthToken newRefreshToken = authTokenProvider.createRefreshToken();
            //DB Refresh Token 업데이트
            dbRefreshToken.updateToken(newRefreshToken.getToken());

            return TokenResponse.builder()
                    .accessToken(newAccessToken.getToken())
                    .refreshToken(newRefreshToken.getToken())
                    .build();
        }
        throw new CustomException(INVALID_REFRESH_TOKEN);
    }
}
