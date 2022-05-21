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
        User user = googleUserInfo.getUser(authRequest.getIdToken()); //구글에서 받아온 이용자 정보
        String providerId = user.getProviderId();

        Optional<User> userOptional = userRepository.findByProviderId(providerId);
        Long userId;
        boolean isNewUser;

        //기존 사용자
        if (userOptional.isPresent()) {
            //기존 사용자 정보 업데이트 (by. dirty checking)
            User findUser = userOptional.get();
            findUser.updateUser(user);
            userId = findUser.getId();
            isNewUser = false;
        }
        //새로운 사용자
        else {
            userRepository.save(user);
            userId = user.getId();
            isNewUser = true;
        }

        AuthToken accessToken = authTokenProvider.createAccessToken(providerId, userId);
        AuthToken refreshToken = authTokenProvider.createRefreshToken();

        //refreshToken DB에 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .key(providerId)
                .value(refreshToken.getToken())
                .build());

        return AuthResponse.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .isNewUser(isNewUser)
                .build();
    }
}
