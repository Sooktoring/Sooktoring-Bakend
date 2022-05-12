package com.project.sooktoring.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "구글 엑세스 토큰", description = "구글 리소스 서버 접근 위한 엑세스 토큰 전달")
public class AuthRequest {

    @ApiModelProperty(value = "구글 엑세스 토큰")
    private String accessToken;
}
