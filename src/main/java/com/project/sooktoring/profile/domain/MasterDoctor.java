package com.project.sooktoring.profile.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.enumerate.Degree;

import javax.persistence.*;

import java.time.YearMonth;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

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
}
