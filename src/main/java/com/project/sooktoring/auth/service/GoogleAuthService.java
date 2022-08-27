package com.project.sooktoring.auth.service;

import com.project.sooktoring.profile.domain.AcademicInfo;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.auth.info.GoogleUserInfo;
import com.project.sooktoring.auth.dto.request.AuthRequest;
import com.project.sooktoring.auth.dto.response.AuthResponse;
import com.project.sooktoring.auth.jwt.AuthToken;
import com.project.sooktoring.auth.jwt.AuthTokenProvider;
import com.project.sooktoring.auth.domain.RefreshToken;
import com.project.sooktoring.auth.repository.RefreshTokenRepository;
import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.profile.repository.AcademicInfoRepository;
import com.project.sooktoring.profile.repository.ProfileRepository;
import com.project.sooktoring.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoogleAuthService {

    private final GoogleUserInfo googleUserInfo;
    private final AuthTokenProvider authTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AcademicInfoRepository academicInfoRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
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
            AcademicInfo academicInfo = AcademicInfo.init(loginUser);
            academicInfo = academicInfoRepository.save(academicInfo);
            Profile profile = Profile.init(loginUser, academicInfo);
            profileRepository.save(profile);
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
