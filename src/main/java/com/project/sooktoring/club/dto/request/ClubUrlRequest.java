package com.project.sooktoring.club.dto.request;

import com.project.sooktoring.club.enumerate.ClubUrlCat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "동아리 URL 등록, 수정 시 정보 전달하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubUrlRequest {

    @Schema(description = "동아리 URL id (수정 시 필수)", example = "1")
    private Long clubUrlId;

    @Schema(description = "동아리 URL 카테고리", example = "홈페이지", required = true)
    private ClubUrlCat cat;

    @Schema(description = "동아리 URL", example = "http://", required = true)
    private String url;
}
