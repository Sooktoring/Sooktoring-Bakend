package com.project.sooktoring.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "access token, refresh token, 새로운 사용자인지", description = "구글 이용자 정보 저장후, 생성한 access token, refresh token 반환")
public class AuthResponse {

    @ApiModelProperty(value = "발급된 access token")
    private String accessToken;
    @ApiModelProperty(value = "발급된 refresh token")
    private String refreshToken;
    @ApiModelProperty(value = "새로운 사용자인지 여부")
    private Boolean isNewUser;
}
