package com.project.sooktoring.profile.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.dto.request.CareerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.YearMonth;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Career extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "career_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private YearMonth startDate;

    private YearMonth endDate;

    public static Career create(String job, String company, YearMonth startDate, YearMonth endDate, Profile profile) {
        return Career.builder()
                .job(job)
                .company(company)
                .startDate(startDate)
                .endDate(endDate)
                .build()
                .changeProfile(profile);
    }

    public void update(CareerRequest careerRequest) {
        this.job = careerRequest.getJob();
        this.company = careerRequest.getCompany(); 
        this.startDate = careerRequest.getStartDate();
        this.endDate = careerRequest.getEndDate();
    }

    //연관관계 편의 메소드
    private Career changeProfile(Profile profile) {
        this.profile = profile;
        profile.getCareers().add(this);
        return this;
    }
}
