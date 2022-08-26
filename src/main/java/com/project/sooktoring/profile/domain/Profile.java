package com.project.sooktoring.profile.domain;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.dto.request.ProfileRequest;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Profile extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "academic_info_id")
    private AcademicInfo academicInfo;

    @Builder.Default //없으면 null로 들어감
    @OneToMany(mappedBy = "profile")
    private List<Activity> activities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "profile")
    private List<Career> careers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "profile")
    private List<MasterDoctor> masterDoctorList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "mentorProfile")
    private List<Mentoring> mentoringListToMe = new ArrayList<>(); //현재 이용자가 멘토일 때에만 추가

    @Builder.Default
    @OneToMany(mappedBy = "menteeProfile")
    private List<Mentoring> mentoringListFromMe = new ArrayList<>();

    @Column(name = "profile_image_url")
    private String imageUrl;

    @Column(nullable = false, length = 50)
    private String job;

    private Long workYear;

    @Column(nullable = false, length = 50, unique = true)
    private String nickName;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isMentor = false;

    public static Profile init(User user, AcademicInfo academicInfo) {
        return Profile.builder()
                .user(user)
                .academicInfo(academicInfo)
                .job("")
                .nickName("익명" + user.getId())
                .build();
    }

    public void update(ProfileRequest profileRequest) {
        this.nickName = profileRequest.getRealName();
        this.job = profileRequest.getJob();
        this.workYear = profileRequest.getWorkYear();
        this.isMentor = profileRequest.getIsMentor();
        this.imageUrl = profileRequest.getImageUrl();
    }

    public void changeIsMentor(Boolean isMentor) {
        this.isMentor = isMentor;
    }
}
