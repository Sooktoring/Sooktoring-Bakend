package com.project.sooktoring.auth.service;

import com.project.sooktoring.auth.client.GoogleUserInfo;
import com.project.sooktoring.auth.dto.AuthRequest;
import com.project.sooktoring.auth.dto.AuthResponse;
import com.project.sooktoring.auth.jwt.AuthToken;
import com.project.sooktoring.auth.jwt.AuthTokenProvider;
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
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        User user = googleUserInfo.getUser(authRequest.getAccessToken());
        String providerId = user.getProviderId();
        Optional<User> userOptional = userRepository.findByProviderId(providerId);
        AuthToken appToken = authTokenProvider.createUserAppToken(providerId);

        //기존 사용자
        if (userOptional.isPresent()) {
            return AuthResponse.builder()
                    .appToken(appToken.getToken())
                    .isNewUser(Boolean.FALSE)
                    .build();
        }

        //새로운 사용자
        userRepository.save(user);
        return AuthResponse.builder()
                .appToken(appToken.getToken())
                .isNewUser(Boolean.TRUE)
                .build();
    }
}
