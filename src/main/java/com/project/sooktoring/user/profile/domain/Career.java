package com.project.sooktoring.user.profile.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.user.profile.dto.request.CareerRequest;
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
public class Career extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "career_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private YearMonth startDate;

    private YearMonth endDate;

    public static Career create(String job, String company, YearMonth startDate, YearMonth endDate, UserProfile userProfile) {
        return Career.builder()
                .job(job)
                .company(company)
                .startDate(startDate)
                .endDate(endDate)
                .build()
                .changeUserProfile(userProfile);
    }

    public void update(CareerRequest careerRequest) {
        this.job = careerRequest.getJob();
        this.company = careerRequest.getCompany(); 
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
