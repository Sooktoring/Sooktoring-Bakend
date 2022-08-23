package com.project.sooktoring.club.dto.response;

import com.project.sooktoring.club.enumerate.ClubUrlCat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "동아리 URL 조회 시 반환하는 DTO")
@Getter
@AllArgsConstructor
public class ClubUrlResponse {

    @Schema(description = "동아리 URL id", example = "1")
    private Long clubUrlId;

    @Schema(description = "동아리 URL 카테고리", example = "홈페이지")
    private ClubUrlCat cat;

    @Schema(description = "동아리 URL", example = "http://")
    private String url;
}
