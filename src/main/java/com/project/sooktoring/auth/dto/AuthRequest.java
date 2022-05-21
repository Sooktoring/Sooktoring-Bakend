package com.project.sooktoring.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "구글 id token", description = "인증된 구글 이용자 정보 저장하기 위한 id token 전달")
public class AuthRequest {

    @ApiModelProperty(value = "구글 id token")
    private String idToken;
}
