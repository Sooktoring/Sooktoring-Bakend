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
@ApiModel(value = "앱 토큰, 리프레시 토큰", description = "재발급된 앱 토큰과 리프레시 토큰 반환")
public class TokenResponse {

    @ApiModelProperty(value = "재발급된 앱 토큰")
    private String appToken;
    @ApiModelProperty(value = "재발급된 리프레시 토큰")
    private String refreshToken;
}
