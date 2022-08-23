package com.project.sooktoring.club.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.common.exception.CustomException;

import static com.project.sooktoring.common.exception.ErrorCode.NOT_FOUND_CLUB_URL_CAT;

public enum ClubUrlCat {

    HOMEPAGE("홈페이지"), INSTAGRAM("인스타그램"), TWITTER("트위터"), FACEBOOK("페이스북"), RECRUIT("모집");

    private final String value;

    ClubUrlCat(String value) {
        this.value = value;
    }

    //역직렬화
    @JsonCreator
    public static ClubUrlCat from(String value) {
        for (ClubUrlCat cat : ClubUrlCat.values()) {
            if (cat.getValue().equals(value)) {
                return cat;
            }
        }
        throw new CustomException(NOT_FOUND_CLUB_URL_CAT);
    }

    //직렬화
    @JsonValue
    public String getValue() {
        return value;
    }
}
