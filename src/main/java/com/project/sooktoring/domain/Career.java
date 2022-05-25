package com.project.sooktoring.domain;

import com.project.sooktoring.dto.request.CareerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.YearMonth;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Career {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "career_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;

    @Column(name = "career_details", nullable = false)
    private String details;

    @Column(name = "career_start_date", nullable = false)
    private YearMonth startDate;

    @Column(name = "career_end_date")
    private YearMonth endDate;

    public static Career create(String details, YearMonth startDate, YearMonth endDate, UserProfile userProfile) {
        return Career.builder()
                .details(details)
                .startDate(startDate)
                .endDate(endDate)
                .build()
                .changeUserProfile(userProfile);
    }

    public void update(CareerRequest careerRequest) {
        this.details = careerRequest.getDetails();
        this.startDate = careerRequest.getStartDate();
        this.endDate = careerRequest.getEndDate();
    }

    //연관관계 편의 메소드
    private Career changeUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        userProfile.getCareers().add(this);
        return this;
    }
}
