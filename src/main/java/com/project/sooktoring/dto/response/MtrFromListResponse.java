package com.project.sooktoring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.sooktoring.enumerate.MentoringCat;
import com.project.sooktoring.enumerate.MentoringState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "나의 멘토링 신청내역 리스트 조회 시 반환하는 DTO")
public class MtrFromListResponse {

    @Schema(description = "멘토링 id", example = "1")
    protected Long mtrId;

    @Schema(description = "신청한 멘토 id", example = "1")
    private Long mentorId;

    @Schema(description = "신청한 멘토 실명", example = "김영희")
    private String mentorRealName;

    @Schema(description = "신청한 멘토 프로필 이미지 url")
    private String mentorImageUrl;

    @Schema(description = "멘토링 카테고리", example = "자소서")
    protected MentoringCat cat;

    @Schema(description = "멘토링 상태", example = "APPLY, ACCEPT, REJECT, END, INVALID, WITHDRAW")
    protected MentoringState state;

    @Schema(description = "멘토링 신청날짜", example = "2022/08/01")
    @JsonFormat(pattern = "yyyy/MM/dd", shape = STRING)
    private LocalDateTime createdDate;
}
