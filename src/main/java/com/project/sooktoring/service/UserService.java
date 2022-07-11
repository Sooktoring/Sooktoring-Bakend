package com.project.sooktoring.service;

import com.project.sooktoring.auth.jwt.RefreshTokenRepository;
import com.project.sooktoring.domain.Mentoring;
import com.project.sooktoring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;
    private final MentoringRepository mentoringRepository;

    @Transactional
    public void withdrawById(Long userId) {
        //FK로 삭제
        activityRepository.deleteByUserId(userId);
        careerRepository.deleteByUserId(userId);

        //모든 멘토링 내역(내가 멘토인, 내가 멘티인) WITHDRAW 상태로 변경
        List<Mentoring> mentoringListToMe = mentoringRepository.findByMentorId(userId);
        List<Mentoring> mentoringListFromMe = mentoringRepository.findByMenteeId(userId);
        for (Mentoring mentoring : mentoringListToMe) {
            mentoring.withdraw();
        }
        for (Mentoring mentoring : mentoringListFromMe) {
            mentoring.withdraw();
        }
        //탈퇴하는 이용자의 멘토링 FK set null
        mentoringRepository.updateMentorByUserId(userId);
        mentoringRepository.updateMenteeByUserId(userId);

        //PK로 삭제
        refreshTokenRepository.deleteById(userId);
        userProfileRepository.deleteById(userId);
        userRepository.deleteById(userId);
    }
}
