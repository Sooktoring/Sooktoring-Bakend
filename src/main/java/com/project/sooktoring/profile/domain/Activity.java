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

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Activity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Profile profile;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private YearMonth startDate;

    private YearMonth endDate;

    //연관관계 편의 메소드를 public으로 바꾸고 밖에서 호출하는게 나을라나?
    public static Activity create(String title, String details, YearMonth startDate, YearMonth endDate, Profile profile) {
        return Activity.builder()
                .title(title)
                .details(details)
                .startDate(startDate)
                .endDate(endDate)
                .build()
                .changeProfile(profile);
    }

    public void update(ActivityRequest activityRequest) {
        this.title = activityRequest.getTitle();
        this.details = activityRequest.getDetails();
        this.startDate = activityRequest.getStartDate();
        this.endDate = activityRequest.getEndDate();
    }

    //연관관계 편의 메소드
    private Activity changeProfile(Profile profile) {
        this.profile = profile;
        profile.getActivities().add(this);
        return this;
    }
}
