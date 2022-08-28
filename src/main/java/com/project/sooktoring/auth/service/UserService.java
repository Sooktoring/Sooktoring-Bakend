package com.project.sooktoring.auth.service;

import com.project.sooktoring.common.utils.AcademicInfoUtil;
import com.project.sooktoring.common.utils.MentoringUtil;
import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.common.utils.UserUtil;
import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.auth.repository.RefreshTokenRepository;
import com.project.sooktoring.auth.repository.UserRepository;
import com.project.sooktoring.profile.domain.AcademicInfo;
import com.project.sooktoring.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserUtil userUtil;
    private final AcademicInfoUtil academicInfoUtil;
    private final ProfileUtil profileUtil;
    private final MentoringUtil mentoringUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void withdraw() {
        User user = userUtil.getCurrentUser();
        Profile profile = profileUtil.getCurrentProfile();
        AcademicInfo academicInfo = academicInfoUtil.getCurrentAcademicInfo();

        mentoringUtil.withdraw(profile.getId());
        profileUtil.withdraw(profile);
        academicInfoUtil.withdraw(academicInfo);

        //PK로 삭제
        refreshTokenRepository.findById(user.getId())
                .ifPresent(refreshTokenRepository::delete);
        userRepository.delete(user);
    }
}
