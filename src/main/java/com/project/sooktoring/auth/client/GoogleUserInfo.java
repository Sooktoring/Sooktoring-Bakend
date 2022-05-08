package com.project.sooktoring.auth.client;

import com.project.sooktoring.auth.dto.GoogleUserResponse;
import com.project.sooktoring.auth.exception.TokenValidFailedException;
import com.project.sooktoring.domain.User;
import com.project.sooktoring.enumerate.AuthProvider;
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
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
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
