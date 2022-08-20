package com.project.sooktoring.mentoring.dto.request;

import com.project.sooktoring.mentoring.enumerate.MentoringCat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "멘토링 신청서 수정 시 정보 전달하는 DTO - 멘토링 id는 path variable로 전달")
public class MentoringUpdateRequest {

    @Schema(description = "멘토링 카테고리", example = "자소서")
    @NotNull
    private MentoringCat cat;

    @Schema(description = "멘토링 신청 이유", example = "자소서 첨삭 부탁드립니다!")
    @NotNull
    private String reason;

    @Schema(description = "멘토에게 전하는 말 한마디", example = "감사합니다~")
    @NotNull
    private String talk;
}
