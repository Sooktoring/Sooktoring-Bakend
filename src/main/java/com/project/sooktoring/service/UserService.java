package com.project.sooktoring.service;

import com.project.sooktoring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;
    private final MentoringRepository mentoringRepository;

    @Transactional
    public void withdrawById(Long userId) {
        //FK로 삭제
        activityRepository.deleteByUserId(userId);
        careerRepository.deleteByUserId(userId);

        //탈퇴하는 이용자의 멘토링 FK set null
        mentoringRepository.updateMentorByUserId(userId);
        mentoringRepository.updateMenteeByUserId(userId);

        //PK로 삭제
        userProfileRepository.deleteById(userId);
        userRepository.deleteById(userId);
    }
}
