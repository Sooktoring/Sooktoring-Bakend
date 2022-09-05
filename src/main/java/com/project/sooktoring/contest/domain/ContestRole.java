package com.project.sooktoring.contest.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import lombok.Builder;

import javax.persistence.*;

@Entity
public class ContestRole extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @Column(name = "contest_role_name", length = 50, nullable = false)
    private String name;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isRecruit = true;
}
