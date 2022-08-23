package com.project.sooktoring.club.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.common.exception.CustomException;

import static com.project.sooktoring.common.exception.ErrorCode.*;

public enum ClubKind {

    IN("교내"), OUT("교외");

    private final String value;

    ClubKind(String value) {
        this.value = value;
    }

    //역직렬화
    @JsonCreator
    public static ClubKind from(String value) {
        for (ClubKind kind : ClubKind.values()) {
            if (kind.getValue().equals(value)) {
                return kind;
            }
        }
        throw new CustomException(NOT_FOUND_CLUB_KIND);
    }

    //직렬화
    @JsonValue
    public String getValue() {
        return value;
    }
}
