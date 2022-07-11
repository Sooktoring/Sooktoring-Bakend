package com.project.sooktoring.dto.response;

import com.project.sooktoring.domain.MainMajor;
import com.project.sooktoring.enumerate.MentoringCat;
import com.project.sooktoring.enumerate.MentoringState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "나에게 요청된 멘토링 신청내역 조회 시 반환하는 DTO")
public class MtrToResponse {

    @Schema(description = "멘토링 id", example = "1")
    protected Long mtrId;

    @Schema(description = "신청한 멘티 id", example = "2")
    private Long menteeId;

    @Schema(description = "신청한 멘티 실명", example = "이영희")
    private String menteeRealName;

    @Schema(description = "신청한 멘티 주전공")
    private MainMajor menteeMainMajor;

    @Schema(description = "멘토링 카테고리", example = "자소서")
    protected MentoringCat cat;

    @Schema(description = "멘토링 신청 이유", example = "자소서 첨삭 부탁드립니다!")
    protected String reason;

    @Schema(description = "멘토에게 전하는 말 한마디", example = "감사합니다~")
    protected String talk;

    @Schema(description = "멘토링 상태", example = "APPLY, ACCEPT, REJECT, END, INVALID, WITHDRAW")
    protected MentoringState state;
}
