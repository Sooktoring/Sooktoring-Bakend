package com.project.sooktoring.contest.dto.response;

import com.project.sooktoring.contest.domain.Contest;
import com.project.sooktoring.contest.enumerate.ContestState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.List;

@Schema(description = "공모전 모집 리스트 조회 시 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContestListResponse {

    @Schema(description = "공모전 id", example = "1")
    private Long contestId;

    @Schema(description = "공모전 이름", example = "교내 해커톤")
    private String contestName;

    @Schema(description = "공모전 모집상태", example = "모집 완료")
    private ContestState contestState;

    @Schema(description = "모집 d-day", example = "7")
    private Long dDay;

    @Schema(description = "공모전 모집역할 리스트")
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
