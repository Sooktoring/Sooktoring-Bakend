package com.project.sooktoring.contest.dto.response;

import com.project.sooktoring.contest.domain.ContestRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContestRoleResponse {

    private Long contestRoleId;

    private String contestRoleName;

    private Boolean isRecruit;

    public static ContestRoleResponse create(ContestRole contestRole) {
        return ContestRoleResponse.builder()
                .contestRoleId(contestRole.getId())
                .contestRoleName(contestRole.getName())
                .isRecruit(contestRole.getIsRecruit())
                .build();
    }
}
