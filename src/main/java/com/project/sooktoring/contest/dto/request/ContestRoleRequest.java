package com.project.sooktoring.contest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공모전 모집역할 등록, 수정 시 정보 전달하는 DTO")
@Getter
public class ContestRoleRequest {

    @Schema(description = "공모전 모집역할 id", example = "1")
    private Long contestRoleId;

    @Schema(description = "공모전 모집역할 이름", example = "서버")
    private String contestRoleName;

    @Schema(description = "모집중 여부", example = "true")
    private Boolean isRecruit;
}
