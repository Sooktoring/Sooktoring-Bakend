package com.project.sooktoring.profile.domain;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.user.domain.User;
import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.dto.request.ProfileRequest;
import lombok.*;

import javax.persistence.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default //없으면 null로 들어감
    @OneToMany(mappedBy = "profile")
    private List<Activity> activities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "profile")
    private List<Career> careers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "mentorProfile")
    private List<Mentoring> mentoringListToMe = new ArrayList<>(); //현재 이용자가 멘토일 때에만 추가

    @Builder.Default
    @OneToMany(mappedBy = "menteeProfile")
    private List<Mentoring> mentoringListFromMe = new ArrayList<>();

    //User의 name은 실명 아닐 수 있음 -> 프로필 등록 시 직접 입력받음 (일단 실명 인증 API 생략)
    @Column(nullable = false)
    private String realName;

    @Embedded
    @Column(nullable = false)
    private MainMajor mainMajor;

    @Embedded
    private DoubleMajor doubleMajor;

    @Embedded
    private Minor minor;

    @Column(nullable = false)
    private YearMonth entranceDate; //년월만 다룸

    private YearMonth graduationDate; //졸업예정 년월?

    private String job;

    private Long workYear;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isMentor = false;

    private String imageUrl;

    public static Profile initByUser(User user, String defaultImageUrl) {
        return Profile.builder()
                .user(user)
                .realName(user.getName())
                .mainMajor(new MainMajor())
                .doubleMajor(new DoubleMajor())
                .minor(new Minor())
                .entranceDate(YearMonth.now())
                .graduationDate(YearMonth.now())
                .job("")
                .workYear(0L)
                .imageUrl(defaultImageUrl)
                .build();
    }

    public void update(ProfileRequest userProfileRequest) {
        this.realName = userProfileRequest.getRealName();
        this.mainMajor = userProfileRequest.getMainMajor();
        this.doubleMajor = userProfileRequest.getDoubleMajor();
        this.minor = userProfileRequest.getMinor();
        this.entranceDate = userProfileRequest.getEntranceDate();
        this.graduationDate = userProfileRequest.getGraduationDate();
        this.job = userProfileRequest.getJob();
        this.workYear = userProfileRequest.getWorkYear();
        this.isMentor = userProfileRequest.getIsMentor();
        this.imageUrl = userProfileRequest.getImageUrl();
    }
}
