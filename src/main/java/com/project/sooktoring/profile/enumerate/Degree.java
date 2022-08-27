package com.project.sooktoring.profile.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.common.exception.CustomException;

import static com.project.sooktoring.common.exception.ErrorCode.NOT_FOUND_DEGREE;

public enum Degree {

    MASTER("석사"), DOCTOR("박사");

    private final String value;

    Degree(String value) {
        this.value = value;
    }

    //역직렬화
    @JsonCreator
    public static Degree from(String value) {
        for (Degree degree : Degree.values()) {
            if (degree.getValue().equals(value)) {
                return degree;
            }
        }
        throw new CustomException(NOT_FOUND_DEGREE);
    }

    //직렬화
    @JsonValue
    public String getValue() {
        return value;
    }
}
