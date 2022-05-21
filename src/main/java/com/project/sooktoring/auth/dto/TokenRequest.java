package com.project.sooktoring.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "access token, refresh token", description = "access token 만료시, access token과 refresh token 전달")
public class TokenRequest {

    @ApiModelProperty(value = "만료된 access token")
    private String accessToken;
    @ApiModelProperty(value = "access token 재발급하기 위한 refresh token")
    private String refreshToken;
}
