package com.project.sooktoring.profile.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.common.exception.CustomException;

import static com.project.sooktoring.common.exception.ErrorCode.*;

public enum Major {
    CULTURE_TOURISM_MAJOR("문화관광학전공"),
    LE_CORDON_BLUE_MAJOR("르꼬르동블루외식경영전공"),
    IT_ENGINEERING_MAJOR("IT공학전공"),
    ELECTRONIC_ENGINEERING_MAJOR("전자공학전공"),
    PHYSICS_MAJOR("응용물리전공"),
    COMPUTER_SCIENCE_MAJOR("컴퓨터과학전공"),
    SW_MAJOR("SW융합전공"),
    GLOBAL_COOPERATION_MAJOR("글로벌협력전공"),
    ENTREPRENEURSHIP_MAJOR("앙트러프러너십전공"),
    ENGLISH_LITERATURE_MAJOR("영어영문학전공"),
    TESL_MAJOR("테슬(TESL)전공"),
    DEFAULT("");

    private final String name;

    Major(String name) {
        this.name = name;
    }

    //역직렬화
    @JsonCreator
    public static Major from(String name) {
        for (Major major : Major.values()) {
            if (major.getName().equals(name)) {
                return major;
            }
        }
        throw new CustomException(NOT_FOUND_MAJOR_CAT);
    }

    //직렬화
    @JsonValue
    public String getName() {
        return name;
    }
}
