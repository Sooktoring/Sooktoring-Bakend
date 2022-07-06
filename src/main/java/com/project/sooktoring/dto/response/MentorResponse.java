package com.project.sooktoring.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sooktoring.domain.MainMajor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "멘토링에서 멘토 프로필 조회 시 반환하는 DTO")
public class MentorResponse {

    @Schema(description = "멘토 id", example = "1")
    @JsonProperty("mentorId")
    private Long id;

    @Schema(description = "멘토 이름", example = "김멘토")
    private String name; //조회시 user, user_profile 테이블 조인 필요 -> user_profile에 name 필드 추가?

    @Schema(description = "현재 직업", example = "프론트엔드 개발자")
    private String job;

    @Schema(description = "현재 직업 연차", example = "1")
    private Long workYear;

    @Schema(description = "주전공")
    private MainMajor mainMajor;
}
