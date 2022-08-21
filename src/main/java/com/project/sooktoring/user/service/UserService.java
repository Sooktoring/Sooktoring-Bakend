package com.project.sooktoring.user.service;

import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.common.utils.UserUtil;
import com.project.sooktoring.mentoring.service.MentoringService;
import com.project.sooktoring.profile.service.ProfileService;
import com.project.sooktoring.user.domain.User;
import com.project.sooktoring.user.repository.RefreshTokenRepository;
import com.project.sooktoring.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ProfileService profileService;
    private final MentoringService mentoringService;

    @Transactional
    public void withdraw() {
        User user = userUtil.getCurrentUser();
        Long profileId = profileUtil.getCurrentProfile().getId();

        mentoringService.withdraw(profileId);
        profileService.withdraw(profileId);

        //PK로 삭제
        refreshTokenRepository.findById(user.getId())
                .ifPresent(refreshTokenRepository::delete);
        userRepository.delete(user);
    }
}
