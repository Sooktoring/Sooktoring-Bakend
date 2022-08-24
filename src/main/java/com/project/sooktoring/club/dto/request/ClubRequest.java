package com.project.sooktoring.club.dto.request;

import com.project.sooktoring.club.enumerate.ClubKind;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "동아리 등록, 수정 시 정보 전달하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubRequest {

    @Schema(description = "동아리 종류", example = "교내", required = true)
    private ClubKind kind;

    @Schema(description = "동아리명", example = "SOLUX", required = true)
    private String name;

    @Schema(description = "동아리 소개", example = "숙명 중앙 개발 동아리", required = true)
    private String desc;

    @Schema(description = "모집분야", example = "백엔드", required = true)
    private String recruitField;

    @Schema(description = "모집시기", example = "매년3월초", required = true)
    private String recruitTime;

    @Schema(description = "모집중 여부", example = "false", required = true)
    private Boolean isRecruit;

    @Schema(description = "동아리 URL 리스트")
    private List<ClubUrlRequest> clubUrlList;
}
