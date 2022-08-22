package com.project.sooktoring.mentoring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "나에게 온 멘토링 감사카드 조회 시 반환하는 DTO")
@Getter
@AllArgsConstructor
public class MentoringCardToResponse {

    @Schema(description = "멘토링 감사카드 id", example = "1")
    protected Long mentoringCardId;

    @Schema(description = "멘티 프로필 id", example = "2")
    private Long menteeProfileId;

    @Schema(description = "멘티 프로필 이미지 url")
    private String menteeImageUrl;

    @Schema(description = "감사카드 제목", example = "감사합니다.")
    private String title;

    @Schema(description = "감사카드 내용", example = "자소서 첨삭 감사드립니다.")
    private String content;
}
