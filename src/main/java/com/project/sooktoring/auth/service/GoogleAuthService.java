package com.project.sooktoring.auth.service;

import com.project.sooktoring.auth.user.GoogleUserInfo;
import com.project.sooktoring.auth.dto.AuthRequest;
import com.project.sooktoring.auth.dto.AuthResponse;
import com.project.sooktoring.auth.jwt.AuthToken;
import com.project.sooktoring.auth.jwt.AuthTokenProvider;
import com.project.sooktoring.auth.jwt.RefreshToken;
import com.project.sooktoring.auth.jwt.RefreshTokenRepository;
import com.project.sooktoring.domain.User;
import com.project.sooktoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleUserInfo googleUserInfo;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        User user = googleUserInfo.getUser(authRequest.getIdToken());
        String providerId = user.getProviderId();
        Optional<User> userOptional = userRepository.findByProviderId(providerId);

        AuthToken accessToken = authTokenProvider.createAccessToken(providerId);
        AuthToken refreshToken = authTokenProvider.createRefreshToken();

        //refreshToken DB에 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .key(providerId)
                .value(refreshToken.getToken())
                .build());

        //기존 사용자
        if (userOptional.isPresent()) {
            //기존 사용자 정보 업데이트 (by. dirty checking)
            User dbUser = userOptional.get();
            dbUser.updateUser(user);

            return AuthResponse.builder()
                    .accessToken(accessToken.getToken())
                    .refreshToken(refreshToken.getToken())
                    .isNewUser(Boolean.FALSE)
                    .build();
        }

        //새로운 사용자
        userRepository.save(user);
        return AuthResponse.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .isNewUser(Boolean.TRUE)
                .build();
    }
}
