package com.project.sooktoring.auth.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.auth.dto.request.TokenRequest;
import com.project.sooktoring.auth.dto.response.TokenResponse;
import com.project.sooktoring.auth.jwt.AuthToken;
import com.project.sooktoring.auth.jwt.AuthTokenProvider;
import com.project.sooktoring.auth.domain.RefreshToken;
import com.project.sooktoring.auth.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
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
        Claims claims = accessToken.getExpiredTokenClaims();
        if (claims == null) throw new CustomException(INVALID_ACCESS_TOKEN);

        //Refresh Token 검증
        if(refreshToken.validate()) {
            //Access Token에서 providerId get
            String providerId = claims.getSubject();
            Long userId = claims.get("userId", Long.class);

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
