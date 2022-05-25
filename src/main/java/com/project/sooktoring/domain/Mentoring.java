package com.project.sooktoring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Mentoring {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mtr_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
    private UserProfile userProfile;

    @Column(nullable = false)
    private Long mentorId; //** 제약조건 추가하기!

    @Enumerated(value = STRING)
    @Column(name = "mtr_cat", nullable = false)
    private MentoringCat cat = MentoringCat.INTRODUCTION;

    @Column(name = "mtr_reason", nullable = false)
    private String reason;
    @Column(name = "mtr_talk", nullable = false)
    private String talk;
    @Column(nullable = false)
    private boolean isAccept = false;
}
