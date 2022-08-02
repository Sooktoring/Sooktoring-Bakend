package com.project.sooktoring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "멘토링 감사카드 등록, 수정 시 정보 전달하는 DTO")
public class MtrCardRequest {

    @Schema(description = "감사카드 제목", example = "감사합니다.")
    @NotNull
    private String title;

    @Schema(description = "감사카드 내용", example = "자소서 첨삭 감사드립니다.")
    @NotNull
    private String content;
}
