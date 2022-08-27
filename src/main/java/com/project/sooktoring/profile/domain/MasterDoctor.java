package com.project.sooktoring.profile.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.dto.request.MasterDoctorRequest;
import com.project.sooktoring.profile.enumerate.Degree;
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
public class MasterDoctor extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "master_doctor_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Degree degree;

    @Column(nullable = false)
    private YearMonth entranceDate;

    private YearMonth graduationDate;

    @Column(nullable = false)
    private Boolean isAttend;

    @Column(nullable = false, length = 50)
    private String univName;

    @Column(nullable = false, length = 50)
    private String mainMajor;

    public static MasterDoctor create(MasterDoctorRequest masterDoctorRequest, Profile profile) {
        return MasterDoctor.builder()
                .degree(masterDoctorRequest.getDegree())
                .entranceDate(masterDoctorRequest.getEntranceDate())
                .graduationDate(masterDoctorRequest.getGraduationDate())
                .isAttend(masterDoctorRequest.getIsAttend())
                .univName(masterDoctorRequest.getUnivName())
                .mainMajor(masterDoctorRequest.getMainMajor())
                .build()
                .changeProfile(profile);
    }

    public void update(MasterDoctorRequest masterDoctorRequest) {
        this.degree = masterDoctorRequest.getDegree();
        this.entranceDate = masterDoctorRequest.getEntranceDate();
        this.graduationDate = masterDoctorRequest.getGraduationDate();
        this.isAttend = masterDoctorRequest.getIsAttend();
        this.univName = masterDoctorRequest.getUnivName();
        this.mainMajor = masterDoctorRequest.getMainMajor();
    }

    //연관관계 편의 메소드
    private MasterDoctor changeProfile(Profile profile) {
        this.profile = profile;
        profile.getMasterDoctorList().add(this);
        return this;
    }
}
