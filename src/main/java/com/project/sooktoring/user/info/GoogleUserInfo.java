package com.project.sooktoring.user.info;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.user.dto.response.GoogleUserResponse;
import com.project.sooktoring.user.domain.User;
import com.project.sooktoring.user.enumerate.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.project.sooktoring.common.exception.ErrorCode.INVALID_GOOGLE_ID_TOKEN;

@Component
@RequiredArgsConstructor
public class GoogleUserInfo implements UserInfo {

    private final WebClient webClient;

    @Override
    public User getUser(String idToken) {
        GoogleUserResponse googleUserResponse = webClient.get()
                //access token 사용시 https://www.googleapis.com/oauth2/v1/userinfo?access_token=
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", idToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new CustomException(INVALID_GOOGLE_ID_TOKEN)))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new CustomException(INVALID_GOOGLE_ID_TOKEN)))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        assert googleUserResponse != null;
        return User.builder()
                .providerId(googleUserResponse.getSub())
                .name(googleUserResponse.getName())
                .email(googleUserResponse.getEmail())
                .provider(AuthProvider.GOOGLE)
                .imageUrl(googleUserResponse.getPicture() != null ? googleUserResponse.getPicture() : "")
                .build();
    }
}
