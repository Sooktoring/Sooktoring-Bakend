package com.project.sooktoring.mentoring.enumerate;

public enum MentoringState {

    /**
     * APPLY : 신청 상태,
     * ACCEPT : 수락 상태,
     * REJECT : 거절 상태,
     * END_MENTOR : 종료 요청 상태 (by.멘토),
     * END_MENTEE : 종료 요청 상태 (by.멘티),
     * END : 종료 상태 (by.멘토-멘티 상호동의),
     * WITHDRAW : 멘토, 멘티 중 하나 또는 모두 탈퇴한 상태 
     */
    APPLY, ACCEPT, REJECT, END_MENTOR, END_MENTEE, END, WITHDRAW
}
