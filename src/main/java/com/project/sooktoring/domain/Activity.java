package com.project.sooktoring.domain;

import com.project.sooktoring.dto.request.ActivityRequest;
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
public class Activity {

    @Id @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;

    @Column(name = "activity_details", nullable = false)
    private String details;

    @Column(name = "activity_start_date", nullable = false)
    private YearMonth startDate;

    @Column(name = "activity_end_date")
    private YearMonth endDate;

    public static Activity create(String details, YearMonth startDate, YearMonth endDate, UserProfile userProfile) {
        return Activity.builder()
                .details(details)
                .startDate(startDate)
                .endDate(endDate)
                .build()
                .changeUserProfile(userProfile);
    }

    public void update(ActivityRequest activityRequest) {
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
