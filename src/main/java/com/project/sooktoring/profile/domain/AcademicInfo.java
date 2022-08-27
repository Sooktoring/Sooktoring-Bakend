package com.project.sooktoring.profile.domain;

import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.dto.request.AcademicInfoRequest;
import com.project.sooktoring.profile.enumerate.AcademicStatus;
import com.project.sooktoring.profile.enumerate.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.YearMonth;

import static com.project.sooktoring.profile.enumerate.AcademicStatus.*;
import static com.project.sooktoring.profile.enumerate.Major.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class AcademicInfo extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "academic_info_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "id_image_url")
    private String imageUrl;

    @Column(nullable = false, length = 50)
    private String realName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcademicStatus academicStatus = ATTEND;

    @Builder.Default
    @Column(nullable = false)
    private YearMonth entranceDate = YearMonth.now();

    @Builder.Default
    private YearMonth graduationDate = YearMonth.now();

    @Builder.Default
    @Column(nullable = false)
    private Boolean isGraduation = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Major mainMajor = DEFAULT;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Major doubleMajor = DEFAULT;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Major minorMajor = DEFAULT;

    public static AcademicInfo init(User user) {
        return AcademicInfo.builder()
                .user(user)
                .realName(user.getName())
                .build();
    }

    public void update(AcademicInfoRequest academicInfoRequest) {
        this.realName = academicInfoRequest.getRealName();
        this.academicStatus = academicInfoRequest.getAcademicStatus();
        this.entranceDate = academicInfoRequest.getEntranceDate();
        this.graduationDate = academicInfoRequest.getGraduationDate();
        this.isGraduation = academicInfoRequest.getIsGraduation();
        this.mainMajor = academicInfoRequest.getMainMajor();
        this.doubleMajor = academicInfoRequest.getDoubleMajor();
        this.minorMajor = academicInfoRequest.getMinorMajor();
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
