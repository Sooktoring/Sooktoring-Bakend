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
@ApiModel(value = "access token, refresh token", description = "재발급된 access token과 refresh token 반환")
public class TokenResponse {

    @ApiModelProperty(value = "재발급된 access token")
    private String accessToken;
    @ApiModelProperty(value = "재발급된 refresh token")
    private String refreshToken;
}
