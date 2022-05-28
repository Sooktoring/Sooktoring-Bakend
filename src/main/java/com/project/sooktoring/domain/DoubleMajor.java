package com.project.sooktoring.domain;

import com.project.sooktoring.enumerate.Dept;
import com.project.sooktoring.enumerate.Major;
import com.project.sooktoring.enumerate.Univ;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.*;

@Getter
@Embeddable
public class DoubleMajor {

    @Enumerated(value = STRING)
    @Column(name = "double_univ")
    private Univ univ;

    @Enumerated(value = STRING)
    @Column(name = "double_dept")
    private Dept dept;

    @Enumerated(value = STRING)
    @Column(name = "double_major")
    private Major major;
}
