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
@ApiModel(value = "앱 토큰, 리프레시 토큰, 새로운 사용자인지", description = "구글 리소스 서버 접근후, 생성한 앱 토큰, 리프레시 토큰 반환")
public class AuthResponse {

    @ApiModelProperty(value = "발급된 앱 토큰")
    private String appToken;
    @ApiModelProperty(value = "발급된 리프레시 토큰")
    private String refreshToken;
    @ApiModelProperty(value = "새로운 사용자인지 여부")
    private Boolean isNewUser;
}
