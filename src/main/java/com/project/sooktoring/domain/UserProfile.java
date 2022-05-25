package com.project.sooktoring.domain;

import com.project.sooktoring.dto.request.UserProfileRequest;
import lombok.*;

import javax.persistence.*;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile implements Serializable {

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
    @OneToMany(mappedBy = "userProfile")
    private List<Mentoring> mentoringList = new ArrayList<>();

    //아래 3개 enum 타입으로 바꿀 것
    @Column(nullable = false)
    private String mainMajor;
    private String doubleMajor;
    private String minor;

    @Column(nullable = false)
    private YearMonth entranceDate; //년월만 다룸
    private YearMonth gradDate;

    @Column(name = "user_job", nullable = false)
    private String job;
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
        this.isMentor = userProfileRequest.getIsMentor();
    }
}
