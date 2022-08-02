package com.project.sooktoring.user.profile.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.user.profile.dto.request.ActivityRequest;
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
    private UserProfile userProfile;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private YearMonth startDate;

    private YearMonth endDate;

    //연관관계 편의 메소드를 public으로 바꾸고 밖에서 호출하는게 나을라나?
    public static Activity create(String title, String details, YearMonth startDate, YearMonth endDate, UserProfile userProfile) {
        return Activity.builder()
                .title(title)
                .details(details)
                .startDate(startDate)
                .endDate(endDate)
                .build()
                .changeUserProfile(userProfile);
    }

    public void update(ActivityRequest activityRequest) {
        this.title = activityRequest.getTitle();
        this.details = activityRequest.getDetails();
        this.startDate = activityRequest.getStartDate();
        this.endDate = activityRequest.getEndDate();
    }

    //연관관계 편의 메소드
    private Activity changeUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        userProfile.getActivities().add(this);
        return this;
    }
}
