package com.project.sooktoring.common.utils;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.profile.repository.ActivityRepository;
import com.project.sooktoring.profile.repository.CareerRepository;
import com.project.sooktoring.profile.repository.MasterDoctorRepository;
import com.project.sooktoring.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Component
public class ProfileUtil {

    private final S3Uploader s3Uploader;
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

    public void withdraw(Profile profile) {
        Long profileId = profile.getId();
        activityRepository.deleteByProfileId(profileId);
        careerRepository.deleteByProfileId(profileId);
        masterDoctorRepository.deleteByProfileId(profileId);

        //프로필 이미지 삭제
        if (StringUtils.hasText(profile.getImageUrl())) {
            s3Uploader.deleteImg(profile.getImageUrl());
        }
        profileRepository.delete(profile);
    }
}
