package com.project.sooktoring.contest.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.common.exception.CustomException;

import static com.project.sooktoring.common.exception.ErrorCode.NOT_FOUND_CONTEST_STATE;

public enum ContestState {

    URGENCY("긴급 모집 중!"), ING("모집 중!"), COMPLETION("모집 완료");

    private final String value;

    ContestState(String value) {
        this.value = value;
    }

    //역직렬화
    @JsonCreator
    public static ContestState from(String value) {
        for (ContestState state : ContestState.values()) {
            if (state.getValue().equals(value)) {
                return state;
            }
        }
        throw new CustomException(NOT_FOUND_CONTEST_STATE);
    }

    //직렬화
    @JsonValue
    public String getValue() {
        return value;
    }
}
