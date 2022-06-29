package com.project.sooktoring.service;

import com.project.sooktoring.domain.Mentoring;
import com.project.sooktoring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;
    private final MentoringRepository mentoringRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void withdrawById(Long userId) {
        //FK로 삭제
        activityRepository.deleteByUserId(userId);
        careerRepository.deleteByUserId(userId);

        List<Mentoring> mentoringList = mentoringRepository.findByUserId(userId);
        List<Long> ids = mentoringList.stream().map(Mentoring::getId).collect(Collectors.toList());
        if (ids.size() > 0) {
            chatRoomRepository.deleteAllById(ids);
            mentoringRepository.deleteAllById(ids);
        }

        userProfileRepository.deleteById(userId);
        userRepository.deleteById(userId);
    }
}
