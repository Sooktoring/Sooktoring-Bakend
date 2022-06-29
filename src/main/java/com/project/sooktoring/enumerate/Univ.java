package com.project.sooktoring.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.exception.EnumConversionException;

public enum Univ {
    LIBERAL_ARTS_COLLEGE("문과대학"),
    NATURAL_SCIENCES_COLLEGE("이과대학"),
    ENGINEERING_COLLEGE("공과대학"),
    LIFE_SCIENCES_COLLEGE("생활과학대학"),
    SOCIAL_SCIENCES_COLLEGE("사회과학대학"),
    LAW_COLLEGE("법과대학"),
    COMMERCIAL_COLLEGE("경상대학"),
    MUSIC_COLLEGE("음악대학"),
    PHARMACY_COLLEGE("약학대학"),
    ART_COLLEGE("미술대학"),
    BASIC_EDUC_COLLEGE("기초교양대학"),
    DEFAULT("");

    private final String name;

    Univ(String name) {
        this.name = name;
    }

    //역직렬화
    @JsonCreator
    public static Univ from(String name) {
        for (Univ univ : Univ.values()) {
            if (univ.getName().equals(name)) {
                return univ;
            }
        }
        throw new EnumConversionException("일치하는 Univ 타입 enum 값이 없습니다.", name);
    }

    //직렬화
    @JsonValue
    public String getName() {
        return name;
    }
}
