package com.project.sooktoring.contest.dto.response;

import com.project.sooktoring.contest.enumerate.ContestState;

import java.util.List;

public class ContestListResponse {

    private Long contestId;

    private String contestName;

    private ContestState contestState;

    private Long dDay;

    private List<ContestRoleResponse> contestRoles;
}
