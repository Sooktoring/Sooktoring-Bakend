package com.project.sooktoring.contest.dto.response;

import com.project.sooktoring.contest.domain.Contest;
import com.project.sooktoring.contest.enumerate.ContestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContestListResponse {

    private Long contestId;

    private String contestName;

    private ContestState contestState;

    private Long dDay;

    private List<ContestRoleResponse> contestRoles;

    private static final long SEC_OF_3DAYS = 259200;

    public void changeContestRoles(List<ContestRoleResponse> contestRoles) {
        this.contestRoles = contestRoles;
    }

    public static ContestListResponse create(Contest contest) {
        ContestState contestState = getContestState(contest.getDeadline());
        Long dDay = getDDay(contest.getDeadline());
        return ContestListResponse.builder()
                .contestId(contest.getId())
                .contestName(contest.getName())
                .contestState(contestState)
                .dDay(dDay)
                .build();
    }

    private static ContestState getContestState(LocalDateTime deadline) {
        long secGap = Duration.between(LocalDateTime.now(ZoneId.of("Asia/Seoul")), deadline).toSeconds();
        if (secGap > SEC_OF_3DAYS) return ContestState.ING;
        else if(secGap < 0) return ContestState.COMPLETION;
        else return ContestState.URGENCY;
    }

    //d-day는 날짜만
    private static Long getDDay(LocalDateTime deadline) {
        LocalDateTime startDate = LocalDate.now(ZoneId.of("Asia/Seoul")).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(deadline.getYear(), deadline.getMonth(), deadline.getDayOfMonth()).atStartOfDay();
        return Duration.between(startDate, endDate).toDays();
    }
}
