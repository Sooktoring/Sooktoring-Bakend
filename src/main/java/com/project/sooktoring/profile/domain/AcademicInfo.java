package com.project.sooktoring.profile.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.enumerate.AcademicStatus;
import com.project.sooktoring.profile.enumerate.Major;

import javax.persistence.*;

import java.time.YearMonth;

import static com.project.sooktoring.profile.enumerate.Major.*;
import static javax.persistence.GenerationType.*;

@Entity
public class AcademicInfo extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "academic_info_id")
    private Long id;

    @Column(name = "id_image_url")
    private String imageUrl;

    @Column(nullable = false, length = 50)
    private String realName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcademicStatus academicStatus;

    @Column(nullable = false)
    private YearMonth entranceDate;

    private YearMonth graduationDate;

    @Column(nullable = false)
    private Boolean isGraduation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Major mainMajor = DEFAULT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Major doubleMajor = DEFAULT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Major minorMajor = DEFAULT;
}
