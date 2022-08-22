package com.project.sooktoring.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "엑세스 토큰 만료시 엑세스, 리프레시 토큰 전달하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {

    @Schema(description = "만료된 엑세스 토큰")
    private String accessToken;

    @Schema(description = "엑세스 토큰 재발급 받기 위한 리프레시 토큰")
    private String refreshToken;
}
