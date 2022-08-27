package com.project.sooktoring.profile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenteeProfileResponse {

    private Long profileId;

    private String profileImageUrl;

    private String job;

    private Long workYear;

    private String nickName;

    private Boolean isMentor;

    private List<ActivityResponse> activityList;

    public MenteeProfileResponse(Long profileId, String profileImageUrl, String job, Long workYear, String nickName, Boolean isMentor) {
        this.profileId = profileId;
        this.profileImageUrl = profileImageUrl;
        this.job = job;
        this.workYear = workYear;
        this.nickName = nickName;
        this.isMentor = isMentor;
    }

    public void changeList(List<ActivityResponse> activityList) {
        this.activityList = activityList;
    }
}
