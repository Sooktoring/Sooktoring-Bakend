package com.project.sooktoring.contest.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
public class ContestRequest {

    private String contestName;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", shape = STRING)
    private LocalDateTime deadline;

    private List<ContestRoleRequest> contestRoles;
}
