package com.project.sooktoring.domain;

import com.project.sooktoring.enumerate.Dept;
import com.project.sooktoring.enumerate.Major;
import com.project.sooktoring.enumerate.Univ;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Getter
@Embeddable
public class Minor {

    @Enumerated(value = STRING)
    @Column(name = "minor_univ")
    private Univ univ;

    @Enumerated(value = STRING)
    @Column(name = "minor_dept")
    private Dept dept;

    @Enumerated(value = STRING)
    @Column(name = "minor_major")
    private Major major;
}
