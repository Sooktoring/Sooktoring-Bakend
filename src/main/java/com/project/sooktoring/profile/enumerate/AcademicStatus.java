package com.project.sooktoring.profile.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.common.exception.CustomException;

import static com.project.sooktoring.common.exception.ErrorCode.NOT_FOUND_ACADEMIC_STATUS;

public enum AcademicStatus {

    ATTEND("재학"), BREAK("휴학"), GRADUATION("졸업"), COMPLETION("수료"), DELAY("유예");

    private final String value;

    AcademicStatus(String value) {
        this.value = value;
    }

    //역직렬화
    @JsonCreator
    public static AcademicStatus from(String value) {
        for (AcademicStatus status : AcademicStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new CustomException(NOT_FOUND_ACADEMIC_STATUS);
    }

    //직렬화
    @JsonValue
    public String getValue() {
        return value;
    }
}
