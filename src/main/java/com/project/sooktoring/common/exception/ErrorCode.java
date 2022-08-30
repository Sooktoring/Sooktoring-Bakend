package com.project.sooktoring.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_ACCESS_TOKEN(UNAUTHORIZED, "유효하지 않은 엑세스 토큰입니다."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_GOOGLE_ID_TOKEN(UNAUTHORIZED, "유효하지 않은 구글 id 토큰입니다."),

    NOT_FOUND_USER(NOT_FOUND, "해당 이용자를 찾을 수 없습니다."),
    NOT_FOUND_FCM_TOKEN(NOT_FOUND, "현재 이용자의 FCM 토큰을 찾을 수 없습니다."),
    NOT_FOUND_ACADEMIC_INFO(NOT_FOUND, "해당 학적정보를 찾을 수 없습니다."),
    NOT_FOUND_PROFILE(NOT_FOUND, "해당 프로필을 찾을 수 없습니다."),
    NOT_FOUND_MENTOR(NOT_FOUND, "해당 멘토를 찾을 수 없습니다."),
    NOT_FOUND_MENTEE(NOT_FOUND, "해당 멘티를 찾을 수 없습니다."),
    NOT_FOUND_MASTER_DOCTOR(NOT_FOUND, "해당 학사 이후 학력을 찾을 수 없습니다."),
    NOT_FOUND_CAREER(NOT_FOUND, "해당 직무경력을 찾을 수 없습니다."),
    NOT_FOUND_ACTIVITY(NOT_FOUND, "해당 대외활동을 찾을 수 없습니다."),
    NOT_FOUND_MAJOR_CAT(NOT_FOUND, "해당 전공명을 찾을 수 없습니다."),
    NOT_FOUND_ACADEMIC_STATUS(NOT_FOUND, "해당 학적상태를 찾을 수 없습니다."),
    NOT_FOUND_DEGREE(NOT_FOUND, "해당 학위를 찾을 수 없습니다."),
    FAILED_FILE_UPLOAD(INTERNAL_SERVER_ERROR, "이미지 파일 업로드에 실패하였습니다."),
    INVALID_ACADEMIC_STATUS(BAD_REQUEST, "멘토의 학적상태는 졸업 or 수료 or 유예 중 하나이어야 합니다."),
    ALREADY_NICKNAME_EXISTS(BAD_REQUEST, "해당 닉네임이 이미 존재합니다."),

    ALREADY_MENTORING_EXISTS(BAD_REQUEST, "같은 멘토링 신청내역이 이미 존재합니다."),
    ALREADY_MENTORING_CARD_EXISTS(BAD_REQUEST, "해당 멘토링에 대한 감사카드가 이미 존재합니다."),
    INVALID_MENTORING_TO_SELF(BAD_REQUEST, "자신에게 멘토링 신청은 불가능합니다."),
    INVALID_MENTORING_TO_MENTEE(BAD_REQUEST, "멘티에게 멘토링 신청은 불가능합니다."),
    NOT_FOUND_MENTORING(NOT_FOUND, "해당 멘토링을 찾을 수 없습니다,"),
    NOT_FOUND_MENTORING_CARD(NOT_FOUND, "해당 멘토링 카드를 찾을 수 없습니다."),
    NOT_FOUND_MENTORING_CAT(NOT_FOUND, "해당 멘토링 카테고리를 찾을 수 없습니다."),
    FORBIDDEN_MENTORING_ACCEPT(FORBIDDEN, "멘토링 신청상태가 아니므로 수락 불가합니다."),
    FORBIDDEN_MENTORING_REJECT(FORBIDDEN, "멘토링 신청상태가 아니므로 거절 불가합니다."),
    FORBIDDEN_MENTORING_END(FORBIDDEN, "멘토링 수락상태가 아니므로 종료 불가합니다."),
    FORBIDDEN_MENTORING_UPDATE(FORBIDDEN, "멘토링 신청상태가 아니므로 변경 불가합니다."),
    FORBIDDEN_MENTORING_CANCEL(FORBIDDEN, "멘토링 수락 또는 종료 상태이므로 취소 불가합니다."),
    FORBIDDEN_MENTORING_CARD_WRITE(FORBIDDEN, "멘토링 종료 상태가 아니므로 감사카드 작성이 불가합니다."),
    FORBIDDEN_MENTORING_ACCESS(FORBIDDEN, "해당 멘토링에 접근할 수 없습니다."),
    FAILED_MENTORING_PUSH(INTERNAL_SERVER_ERROR, "멘토링 푸시 알림에 실패하였습니다."),

    NOT_FOUND_CLUB_KIND(NOT_FOUND, "해당 동아리 종류(교내/교외)를 찾을 수 없습니다."),
    NOT_FOUND_CLUB_URL_CAT(NOT_FOUND, "해당 동아리 URL 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_CLUB(NOT_FOUND, "해당 동아리를 찾을 수 없습니다."),
    NOT_FOUND_CLUB_URL(NOT_FOUND, "해당 동아리 URL을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
