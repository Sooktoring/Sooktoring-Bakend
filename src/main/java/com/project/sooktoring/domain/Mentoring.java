package com.project.sooktoring.domain;

import com.project.sooktoring.common.BaseTimeEntity;
import com.project.sooktoring.enumerate.MentoringCat;
import com.project.sooktoring.enumerate.MentoringState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.project.sooktoring.enumerate.MentoringState.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Mentoring extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mtr_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mentor_id")
    private UserProfile mentorUserProfile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mentee_id")
    private UserProfile menteeUserProfile;

    @Enumerated(value = STRING)
    @Column(name = "mtr_cat", nullable = false)
    private MentoringCat cat;

    @Column(name = "mtr_reason", nullable = false)
    private String reason;

    @Column(name = "mtr_talk", nullable = false)
    private String talk;

    @Builder.Default
    @Column(nullable = false)
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

    public void accept() {
        this.state = ACCEPT;
    }

    public void reject() {
        this.state = REJECT;
    }

    //연관관계 편의 메소드
    public void setMentorMentee(UserProfile mentorUserProfile, UserProfile menteeUserProfile) {
        this.mentorUserProfile = mentorUserProfile;
        this.menteeUserProfile = menteeUserProfile;
        menteeUserProfile.getMentoringListFromMe().add(this);
        mentorUserProfile.getMentoringListToMe().add(this);
    }
}
