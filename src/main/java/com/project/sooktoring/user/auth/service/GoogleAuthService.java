package com.project.sooktoring.user.auth.service;

import com.project.sooktoring.user.auth.info.GoogleUserInfo;
import com.project.sooktoring.user.auth.dto.request.AuthRequest;
import com.project.sooktoring.user.auth.dto.response.AuthResponse;
import com.project.sooktoring.user.auth.jwt.AuthToken;
import com.project.sooktoring.user.auth.jwt.AuthTokenProvider;
import com.project.sooktoring.user.auth.domain.RefreshToken;
import com.project.sooktoring.user.auth.repository.RefreshTokenRepository;
import com.project.sooktoring.user.auth.domain.User;
import com.project.sooktoring.user.profile.domain.UserProfile;
import com.project.sooktoring.user.profile.repository.UserProfileRepository;
import com.project.sooktoring.user.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleUserInfo googleUserInfo;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider authTokenProvider;

    @Value("${cloud.aws.s3.default.image}")
    private String defaultImageUrl;

    @Transactional
    public AuthResponse login(AuthRequest authRequest, HttpServletRequest request) {
        User user = googleUserInfo.getUser(authRequest.getIdToken()); //구글에서 받아온 이용자 정보
        String providerId = user.getProviderId();

        Optional<User> userOptional = userRepository.findByProviderId(providerId);
        User loginUser;
        RefreshToken dbRefreshToken = null;
        boolean isNewUser;

        //기존 사용자
        if (userOptional.isPresent()) {
            //기존 사용자 정보 업데이트 (by. dirty checking)
            loginUser = userOptional.get();
            loginUser.updateUser(user);
            isNewUser = false;

            dbRefreshToken = refreshTokenRepository.findByKey(loginUser.getId()).orElse(null);
        }
        //새로운 사용자
        else {
            loginUser = userRepository.save(user);
            isNewUser = true;

            //기본 유저 프로필 생성
            UserProfile userProfile = UserProfile.initByUser(loginUser, defaultImageUrl);
            userProfileRepository.save(userProfile);
        }

        AuthToken accessToken = authTokenProvider.createAccessToken(providerId, loginUser.getId());
        AuthToken refreshToken = authTokenProvider.createRefreshToken();

        //refreshToken update or insert
        if (!isNewUser && dbRefreshToken != null) {
            dbRefreshToken.updateToken(refreshToken.getToken());
        } else {
            refreshTokenRepository.save(RefreshToken.builder()
                    .loginUser(loginUser)
                    .value(refreshToken.getToken())
                    .build());
        }

        return AuthResponse.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .isNewUser(isNewUser)
                .build();
    }
}
