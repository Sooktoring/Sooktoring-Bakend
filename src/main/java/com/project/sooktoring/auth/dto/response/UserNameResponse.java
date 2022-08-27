package com.project.sooktoring.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "처음 학적정보, 프로필 입력 시 로그인한 유저 이름 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNameResponse {

    @Schema(description = "유저 이름")
    private String userName;
}
