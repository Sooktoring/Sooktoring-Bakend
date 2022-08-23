package com.project.sooktoring.club.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "교내 or 교외 동아리 리스트 조회 시 반환하는 DTO")
@Getter
@AllArgsConstructor
public class ClubListResponse {

    @Schema(description = "동아리 id", example = "1")
    private Long clubId;

    @Schema(description = "동아리 로고 url", example = "http://")
    private String logoUrl;

    @Schema(description = "동아리명", example = "SOLUX")
    private String name;

    @Schema(description = "동아리 소개", example = "숙명 중앙 개발 동아리")
    private String desc;

    @Schema(description = "모집분야", example = "백엔드")
    private String recruitField;
}
