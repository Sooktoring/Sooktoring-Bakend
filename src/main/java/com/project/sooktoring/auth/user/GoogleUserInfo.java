package com.project.sooktoring.auth.user;

import com.project.sooktoring.auth.dto.GoogleUserResponse;
import com.project.sooktoring.auth.exception.GoogleResourceServerAccessException;
import com.project.sooktoring.auth.exception.InvalidGoogleAccessTokenException;
import com.project.sooktoring.domain.User;
import com.project.sooktoring.auth.enumerate.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GoogleUserInfo implements UserInfo {

    private final WebClient webClient;

    @Override
    public User getUser(String accessToken) {
        GoogleUserResponse googleUserResponse = webClient.get()
                //API 요청 url -> id_token 사용 불가 () -> https://oauth2.googleapis.com/tokeninfo?id_token= (현재 profile scope 접근이 안됨...)
                .uri("https://www.googleapis.com/oauth2/v1/userinfo", builder -> builder.queryParam("access_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new InvalidGoogleAccessTokenException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new GoogleResourceServerAccessException()))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        assert googleUserResponse != null;
        return User.builder()
                .providerId(googleUserResponse.getId())
                .name(googleUserResponse.getName())
                .email(googleUserResponse.getEmail())
                .provider(AuthProvider.GOOGLE)
                .imageUrl(googleUserResponse.getPicture() != null ? googleUserResponse.getPicture() : "")
                .build();
    }
}
