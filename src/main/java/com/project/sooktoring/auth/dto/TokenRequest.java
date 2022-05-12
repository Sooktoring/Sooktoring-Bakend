package com.project.sooktoring.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "앱 토큰, 리프레시 토큰", description = "앱 토큰 만료시, 앱 토큰과 리프레시 토큰 전달")
public class TokenRequest {

    @ApiModelProperty(value = "만료된 앱 토큰")
    private String appToken;
    @ApiModelProperty(value = "앱 토큰 재발급하기 위한 리프레시 토큰")
    private String refreshToken;
}
