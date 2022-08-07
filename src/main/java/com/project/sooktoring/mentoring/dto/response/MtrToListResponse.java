package com.project.sooktoring.mentoring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.sooktoring.mentoring.enumerate.MentoringCat;
import com.project.sooktoring.mentoring.enumerate.MentoringState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "나에게 요청된 멘토링 신청내역 리스트 조회 시 반환하는 DTO")
public class MtrToListResponse {

    @Schema(description = "멘토링 id", example = "1")
    protected Long mtrId;

    @Schema(description = "신청한 멘티 id", example = "2")
    private Long menteeId;

    @Schema(description = "신청한 멘티 실명", example = "이영희")
    private String menteeRealName;

    @Schema(description = "신청한 멘티 프로필 이미지 url")
    private String menteeImageUrl;

    @Schema(description = "멘토링 카테고리", example = "자소서")
    protected MentoringCat cat;

    @Schema(description = "멘토링 상태", example = "REJECT")
    protected MentoringState state;

    @Schema(description = "멘토링 신청날짜", example = "2022/08/01")
    @JsonFormat(pattern = "yyyy/MM/dd", shape = STRING)
    private LocalDateTime createdDate;
}
