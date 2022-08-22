package com.project.sooktoring.profile.domain;

import com.project.sooktoring.profile.enumerate.Dept;
import com.project.sooktoring.profile.enumerate.Major;
import com.project.sooktoring.profile.enumerate.Univ;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Getter
@Embeddable
public class Minor {

    @Schema(description = "부전공 소속 대학명")
    @Enumerated(value = STRING)
    @Column(name = "minor_univ")
    private Univ univ;

    @Schema(description = "부전공 학과명")
    @Enumerated(value = STRING)
    @Column(name = "minor_dept")
    private Dept dept;

    @Schema(description = "부전공 전공명")
    @Enumerated(value = STRING)
    @Column(name = "minor_major")
    private Major major;

    public Minor() {
        univ = Univ.DEFAULT;
        dept = Dept.DEFAULT;
        major = Major.DEFAULT;
    }
}
