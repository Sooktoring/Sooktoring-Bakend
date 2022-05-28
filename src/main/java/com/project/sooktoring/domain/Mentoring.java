package com.project.sooktoring.domain;

import com.project.sooktoring.enumerate.MentoringCat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Mentoring {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mtr_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private UserProfile mentorUserProfile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
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
    private Boolean isAccept = false;

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
        this.isAccept = true;
    }

    public void reject() {
        this.isAccept = false;
    }

    //연관관계 편의 메소드
    public void setMentorMentee(UserProfile mentorUserProfile, UserProfile menteeUserProfile) {
        this.mentorUserProfile = mentorUserProfile;
        this.menteeUserProfile = menteeUserProfile;
        menteeUserProfile.getMentoringListFromMe().add(this);
        mentorUserProfile.getMentoringListToMe().add(this);
    }
}
