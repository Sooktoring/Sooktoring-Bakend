package com.project.sooktoring.contest.domain;

import com.project.sooktoring.common.domain.BaseTimeEntity;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contest extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "contest")
    private List<ContestRole> contestRoles = new ArrayList<>();

    @Column(name = "contest_name", length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime deadline;
}
