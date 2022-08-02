package com.project.sooktoring.user.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "구글 아이디 토큰으로 이용자 정보 가져오는 DTO")
public class GoogleUserResponse {

    @Schema(description = "구글 DB 내 Seq 아이디")
    private String sub;

    @Schema(description = "구글 이용자 이메일")
    private String email;

    @Schema(description = "구글 이용자 이름")
    private String name;

    @Schema(description = "구글 이용자 프로필 이미지 url")
    private String picture;
}
