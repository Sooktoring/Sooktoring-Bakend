package com.project.sooktoring.user.auth.info;

import com.project.sooktoring.user.auth.dto.response.GoogleUserResponse;
import com.project.sooktoring.exception.domain.user.auth.GoogleResourceServerAccessException;
import com.project.sooktoring.exception.domain.user.auth.InvalidGoogleIdTokenException;
import com.project.sooktoring.user.auth.domain.User;
import com.project.sooktoring.user.auth.enumerate.AuthProvider;
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
    public User getUser(String idToken) {
        GoogleUserResponse googleUserResponse = webClient.get()
                //access token 사용시 https://www.googleapis.com/oauth2/v1/userinfo?access_token=
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", idToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new InvalidGoogleIdTokenException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new GoogleResourceServerAccessException()))
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
