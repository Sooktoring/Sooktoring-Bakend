package com.project.sooktoring.contest.dto.response;

import com.project.sooktoring.contest.domain.ContestRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공모전 모집역할 조회 시 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContestRoleResponse {

    @Schema(description = "공모전 모집역할 id", example = "1")
    private Long contestRoleId;

    @Schema(description = "공모전 모집역할 이름", example = "서버")
    private String contestRoleName;

    @Schema(description = "모집중 여부", example = "true")
    private Boolean isRecruit;

    public static ContestRoleResponse create(ContestRole contestRole) {
        return ContestRoleResponse.builder()
                .contestRoleId(contestRole.getId())
                .contestRoleName(contestRole.getName())
                .isRecruit(contestRole.getIsRecruit())
                .build();
    }
}
