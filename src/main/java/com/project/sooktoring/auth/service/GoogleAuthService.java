package com.project.sooktoring.auth.service;

import com.project.sooktoring.auth.user.GoogleUserInfo;
import com.project.sooktoring.auth.dto.request.AuthRequest;
import com.project.sooktoring.auth.dto.response.AuthResponse;
import com.project.sooktoring.auth.jwt.AuthToken;
import com.project.sooktoring.auth.jwt.AuthTokenProvider;
import com.project.sooktoring.auth.jwt.RefreshToken;
import com.project.sooktoring.auth.jwt.RefreshTokenRepository;
import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.domain.UserProfile;
import com.project.sooktoring.repository.UserProfileRepository;
import com.project.sooktoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        //Refresh Token 만료에 따른 재로그인일 때, Access Token 만료 시 시작한 세션 종료
        HttpSession session = request.getSession(false); //세션 존재하지 않으면 null 반환
        //세션 존재
        if (session != null && request.isRequestedSessionIdValid()) {
            session.invalidate(); //세션 종료
        }

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
