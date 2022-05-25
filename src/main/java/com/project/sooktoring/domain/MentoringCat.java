package com.project.sooktoring.domain;

public enum MentoringCat {

    INTRODUCTION("자소서"), PERSONALITY("인적성"), INTERVIEW("면접"), PORTFOLIO("포트폴리오");

    private final String value;

    MentoringCat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
