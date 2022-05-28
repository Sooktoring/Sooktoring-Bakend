package com.project.sooktoring.domain;

import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.common.BaseTimeEntity;
import com.project.sooktoring.dto.request.UserProfileRequest;
import lombok.*;

import javax.persistence.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user; //FK이면서 PK

    @Builder.Default //없으면 null로 들어감
    @OneToMany(mappedBy = "userProfile")
    private List<Activity> activities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "userProfile")
    private List<Career> careers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "mentorUserProfile")
    private List<Mentoring> mentoringListToMe = new ArrayList<>(); //현재 이용자가 멘토일 때에만 추가

    @Builder.Default
    @OneToMany(mappedBy = "menteeUserProfile")
    private List<Mentoring> mentoringListFromMe = new ArrayList<>();

    //아래 3개 enum 타입으로 바꿀 것 MajorUnit
    @Embedded
    @Column(nullable = false)
    private MainMajor mainMajor;

    @Embedded
    private DoubleMajor doubleMajor;

    @Embedded
    private Minor minor;

    @Column(nullable = false)
    private YearMonth entranceDate; //년월만 다룸
    private YearMonth gradDate;

    private String job;

    private Long workYear;

    @Column(nullable = false)
    private Boolean isMentor = false;

    public static UserProfile create(UserProfileRequest userProfileRequest, User user) {
        return UserProfile.builder()
                .user(user)
                .mainMajor(userProfileRequest.getMainMajor())
                .doubleMajor(userProfileRequest.getDoubleMajor())
                .minor(userProfileRequest.getMinor())
                .entranceDate(userProfileRequest.getEntranceDate())
                .gradDate(userProfileRequest.getGradDate())
                .job(userProfileRequest.getJob())
                .workYear(userProfileRequest.getWorkYear())
                .isMentor(userProfileRequest.getIsMentor())
                .build();
    }

    public void update(UserProfileRequest userProfileRequest) {
        this.mainMajor = userProfileRequest.getMainMajor();
        this.doubleMajor = userProfileRequest.getDoubleMajor();
        this.minor = userProfileRequest.getMinor();
        this.entranceDate = userProfileRequest.getEntranceDate();
        this.gradDate = userProfileRequest.getGradDate();
        this.job = userProfileRequest.getJob();
        this.workYear = userProfileRequest.getWorkYear();
        this.isMentor = userProfileRequest.getIsMentor();
    }
}
