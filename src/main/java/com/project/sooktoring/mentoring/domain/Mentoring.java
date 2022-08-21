package com.project.sooktoring.mentoring.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.mentoring.enumerate.MentoringCat;
import com.project.sooktoring.mentoring.enumerate.MentoringState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.project.sooktoring.mentoring.enumerate.MentoringState.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Mentoring extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mentoring_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mentor_profile_id")
    private Profile mentorProfile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mentee_profile_id")
    private Profile menteeProfile;

    @Enumerated(value = STRING)
    @Column(name = "mentoring_cat", nullable = false)
    private MentoringCat cat;

    @Column(name = "mentoring_reason", nullable = false)
    private String reason;

    @Column(name = "mentoring_talk", nullable = false)
    private String talk;

    @Builder.Default
    @Enumerated(value = STRING)
    @Column(name = "mentoring_state", nullable = false)
    private MentoringState state = APPLY;

    public static Mentoring create(MentoringCat cat, String reason, String talk) {
        return Mentoring.builder()
                .cat(cat)
                .reason(reason)
                .talk(talk)
                .build();
    }

    public void update(MentoringCat cat, String reason, String talk) {
        this.cat = cat;
        this.reason = reason;
        this.talk = talk;
    }

    public void apply() {
        this.state = APPLY;
    }

    public void accept() {
        this.state = ACCEPT;
    }

    public void reject() {
        this.state = REJECT;
    }

    public void end() {
        this.state = END;
    }

    public void invalid() {
        this.state = INVALID;
    }

    public void withdraw() {
        this.state = WITHDRAW;
    }

    //연관관계 편의 메소드
    public void setMentorAndMentee(Profile mentorProfile, Profile menteeProfile) {
        this.mentorProfile = mentorProfile;
        this.menteeProfile = menteeProfile;
        mentorProfile.getMentoringListToMe().add(this);
        menteeProfile.getMentoringListFromMe().add(this);
    }
}
