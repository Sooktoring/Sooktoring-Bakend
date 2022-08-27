package com.project.sooktoring.profile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "멘토링에서 멘토 프로필 리스트 조회 시 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorProfileListResponse {

    @Schema(description = "멘토 프로필 id", example = "1")
    private Long profileId;

    @Schema(description = "프로필 이미지 url")
    private String profileImageUrl;

    @Schema(description = "현재 직업", example = "프론트엔드 개발자")
    private String job;

    @Schema(description = "현재 직업 연차", example = "1")
    private Long workYear;

    @Schema(description = "멘토 닉네임", example = "김멘토")
    private String nickName;

    @Schema(description = "멘토 여부", example = "false")
    private Boolean isMentor;
}
