package com.project.sooktoring.club.domain;

import com.project.sooktoring.club.enumerate.ClubUrlCat;
import com.project.sooktoring.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClubUrl extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_url_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "club_url_cat", nullable = false)
    private ClubUrlCat cat;

    @Column(name = "club_url", nullable = false, length = 500)
    private String url;

    public void update(ClubUrlCat cat, String url) {
        this.cat = cat;
        this.url = url;
    }
}
