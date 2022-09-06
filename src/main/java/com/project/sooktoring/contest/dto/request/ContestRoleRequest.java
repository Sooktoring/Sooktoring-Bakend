package com.project.sooktoring.contest.dto.request;

import lombok.Getter;

@Getter
public class ContestRoleRequest {

    private Long contestRoleId;

    private String contestRoleName;

    private Boolean isRecruit;
}
