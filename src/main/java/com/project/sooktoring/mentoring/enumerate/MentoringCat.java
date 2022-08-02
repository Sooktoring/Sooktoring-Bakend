package com.project.sooktoring.mentoring.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.exception.global.EnumConversionException;

public enum MentoringCat {

    INTRODUCTION("자소서"), PERSONALITY("인적성"), INTERVIEW("면접"), PORTFOLIO("포트폴리오"), EXPERIENCE("실무경험");

    private final String value;

    MentoringCat(String value) {
        this.value = value;
    }

    //역직렬화
    @JsonCreator
    public static MentoringCat from(String value) {
        for (MentoringCat cat : MentoringCat.values()) {
            if (cat.getValue().equals(value)) {
                return cat;
            }
        }
        throw new EnumConversionException("일치하는 MentoringCat 타입 enum 값이 없습니다.", value);
    }

    //직렬화
    @JsonValue
    public String getValue() {
        return value;
    }
}
