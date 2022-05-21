package com.project.sooktoring.auth.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@ApiModel(value = "에러 DTO", description = "에러 발생시 관련 정보 전달")
public class AuthExResponse {

    @ApiModelProperty(notes = "에러 코드")
    private int status;
    @ApiModelProperty(notes = "에러 메시지")
    private String message;
    @ApiModelProperty(notes = "리다이렉트 할 path")
    private String redirectPath;

    public String convertToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
