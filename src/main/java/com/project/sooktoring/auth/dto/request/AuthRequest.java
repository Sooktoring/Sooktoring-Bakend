package com.project.sooktoring.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "인증된 구글 이용자 정보 저장하기 위해 아이디 토큰 전달하는 DTO")
public class AuthRequest {

    @Schema(description = "구글 아이디 토큰")
    private String idToken;
}
