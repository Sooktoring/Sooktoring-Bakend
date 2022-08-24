package com.project.sooktoring.club.domain;

import com.project.sooktoring.club.dto.request.ClubRequest;
import com.project.sooktoring.club.enumerate.ClubKind;
import com.project.sooktoring.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Club extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "club")
    private List<ClubUrl> clubUrlList = new ArrayList<>();

    @Column(name = "club_logo_url", nullable = false, length = 256)
    private String logoUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "club_kind", nullable = false)
    private ClubKind kind;

    @Column(name = "club_name", nullable = false, length = 50)
    private String name;

    @Column(name = "club_desc", nullable = false, length = 50)
    private String desc;

    @Column(name = "club_recruit_field", nullable = false, length = 50)
    private String recruitField;

    @Column(name = "club_recruit_time", nullable = false, length = 50)
    private String recruitTime;

    @Column(nullable = false)
    private Boolean isRecruit;

    public void update(ClubRequest clubRequest) {
        this.kind = clubRequest.getKind();
        this.name = clubRequest.getName();
        this.desc = clubRequest.getDesc();
        this.recruitField = clubRequest.getRecruitField();
        this.recruitTime = clubRequest.getRecruitTime();
        this.isRecruit = clubRequest.getIsRecruit();
    }

    public void changeLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
