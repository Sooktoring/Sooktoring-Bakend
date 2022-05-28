package com.project.sooktoring.domain;

import com.project.sooktoring.enumerate.Dept;
import com.project.sooktoring.enumerate.Major;
import com.project.sooktoring.enumerate.Univ;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Getter
@Embeddable
public class MainMajor {

    @Schema(description = "주전공 소속 대학명")
    @Enumerated(value = STRING)
    @Column(name = "main_univ")
    private Univ univ;

    @Schema(description = "주전공 학과명")
    @Enumerated(value = STRING)
    @Column(name = "main_dept")
    private Dept dept;

    @Schema(description = "주전공 전공명")
    @Enumerated(value = STRING)
    @Column(name = "main_major")
    private Major major;
}
