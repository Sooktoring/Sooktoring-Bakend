package com.project.sooktoring.common.utils;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.profile.repository.ActivityRepository;
import com.project.sooktoring.profile.repository.CareerRepository;
import com.project.sooktoring.profile.repository.MasterDoctorRepository;
import com.project.sooktoring.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Component
public class ProfileUtil {

    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;
    private final MasterDoctorRepository masterDoctorRepository;

    public Profile getCurrentProfile() {
        return profileRepository.findByUserId(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_PROFILE));
    }

    public Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PROFILE));
    }

    public void withdraw(Long profileId) {
        activityRepository.deleteByProfileId(profileId);
        careerRepository.deleteByProfileId(profileId);
        masterDoctorRepository.deleteByProfileId(profileId);
        profileRepository.deleteById(profileId);
    }
}
