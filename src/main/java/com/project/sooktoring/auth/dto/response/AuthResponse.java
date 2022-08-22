package com.project.sooktoring.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "구글 이용자 정보 저장후, 생성한 엑세스, 리프레시 토큰 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    @Schema(description = "발급된 엑세스 토큰")
    private String accessToken;

    @Schema(description = "발급된 리프레시 토큰")
    private String refreshToken;

    @Schema(description = "새로운 사용자인지 여부")
    private Boolean isNewUser;
}
