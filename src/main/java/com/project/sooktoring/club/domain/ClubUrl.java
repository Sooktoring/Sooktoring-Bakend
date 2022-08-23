package com.project.sooktoring.club.domain;

import com.project.sooktoring.club.enumerate.ClubUrlCat;

import javax.persistence.*;

@Entity
public class ClubUrl {

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
}
