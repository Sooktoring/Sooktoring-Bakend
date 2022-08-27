package com.project.sooktoring.profile.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.dto.request.ActivityRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.YearMonth;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Activity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 200)
    private String details;

    @Column(nullable = false)
    private YearMonth startDate;

    private YearMonth endDate;

    @Column(nullable = false)
    private Boolean isActive;

    //연관관계 편의 메소드를 public으로 바꾸고 밖에서 호출하는게 나을라나?
    public static Activity create(ActivityRequest activityRequest, Profile profile) {
        return Activity.builder()
                .title(activityRequest.getTitle())
                .details(activityRequest.getDetails())
                .startDate(activityRequest.getStartDate())
                .endDate(activityRequest.getEndDate())
                .isActive(activityRequest.getIsActive())
                .build()
                .changeProfile(profile);
    }

    public void update(ActivityRequest activityRequest) {
        this.title = activityRequest.getTitle();
        this.details = activityRequest.getDetails();
        this.startDate = activityRequest.getStartDate();
        this.endDate = activityRequest.getEndDate();
        this.isActive = activityRequest.getIsActive();
    }

    //연관관계 편의 메소드
    private Activity changeProfile(Profile profile) {
        this.profile = profile;
        profile.getActivityList().add(this);
        return this;
    }
}
