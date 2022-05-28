package com.project.sooktoring.auth.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "에러 발생시 관련 정보 반환하는 DTO")
public class AuthExResponse {

    @Schema(description = "에러 코드")
    private int status;

    @Schema(description = "에러 메시지")
    private String message;

    @Schema(description = "리다이렉트 할 path")
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
