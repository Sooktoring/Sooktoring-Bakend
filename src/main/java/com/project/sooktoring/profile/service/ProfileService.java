package com.project.sooktoring.profile.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.service.AwsS3Service;
import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.common.utils.UserUtil;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import com.project.sooktoring.user.domain.User;
import com.project.sooktoring.profile.dto.response.MentorProfileResponse;
import com.project.sooktoring.profile.dto.response.ProfileResponse;
import com.project.sooktoring.profile.domain.Activity;
import com.project.sooktoring.profile.domain.Career;
import com.project.sooktoring.profile.dto.request.ActivityRequest;
import com.project.sooktoring.profile.dto.request.CareerRequest;
import com.project.sooktoring.profile.dto.request.ProfileRequest;
import com.project.sooktoring.profile.repository.ActivityRepository;
import com.project.sooktoring.profile.repository.CareerRepository;
import com.project.sooktoring.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.project.sooktoring.common.exception.ErrorCode.*;
import static com.project.sooktoring.mentoring.enumerate.MentoringState.*;
import static com.project.sooktoring.user.enumerate.Role.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;
    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;
    private final MentoringRepository mentoringRepository; //**
    private final AwsS3Service awsS3Service;

    @Value("${cloud.aws.s3.default.image}")
    private String defaultImageUrl;

    public List<ProfileResponse> getProfiles() {
        return profileRepository.findAllDto();
    }

    public ProfileResponse getProfileDto() {
        Profile profile = profileUtil.getCurrentProfile();
        return profileRepository.findDtoById(profile.getId());
    }

    public ProfileResponse getProfileDto(Long profileId) {
        profileUtil.getProfile(profileId);
        return profileRepository.findDtoById(profileId);
    }

    public List<MentorProfileResponse> getMentorList() {
        return profileRepository.findMentors();
    }

    public MentorProfileResponse getMentor(Long profileId) {
        Profile profile = profileUtil.getProfile(profileId);
        if (profile.getIsMentor()) {
            return profileRepository.findMentor(profileId);
        }
        throw new CustomException(NOT_FOUND_MENTOR);
    }

    //Activity, Career 추가, 수정, 삭제 한번에 수행
    @Transactional
    public void update(ProfileRequest userProfileRequest, MultipartFile file) {
        User user = userUtil.getCurrentUser();
        Long userId = user.getId();
        Profile profile = profileUtil.getCurrentProfile();

        //멘토 -> 멘티 : APPLY -> INVALID, ACCEPT -> END
        if (user.getRole() == ROLE_MENTOR && !userProfileRequest.getIsMentor()) {
            //나에게 온 멘토링 신청내역 APPLY -> INVALID, ACCEPT -> END 로 변경
            List<Mentoring> applyMentoringListToMe = mentoringRepository.findByMentorProfileIdAndState(userId, APPLY);
            for (Mentoring mentoring : applyMentoringListToMe) {
                mentoring.invalid();
            }
            List<Mentoring> acceptMentoringListToMe = mentoringRepository.findByMentorProfileIdAndState(userId, ACCEPT);
            for (Mentoring mentoring : acceptMentoringListToMe) {
                mentoring.end();
            }
        }
        //멘티 -> 멘토 : INVALID -> APPLY
        if (user.getRole() == ROLE_MENTEE && userProfileRequest.getIsMentor()) {
            //이전에 멘토 -> 멘티 -> 멘토로 변경하는 경우 INVALID 상태의 나에게 온 멘토링 신청내역 APPLY 로 변경
            List<Mentoring> invalidMentoringListToMe = mentoringRepository.findByMentorProfileIdAndState(userId, INVALID);
            for (Mentoring mentoring : invalidMentoringListToMe) {
                mentoring.apply();
            }
        }
        user.changeRole(userProfileRequest.getIsMentor()); //User ROLE 업데이트

        String originImageUrl = profile.getImageUrl();
        if (file != null && !file.isEmpty()) {
            if (StringUtils.hasText(originImageUrl) && !originImageUrl.equals(defaultImageUrl)) {
                awsS3Service.deleteImg(originImageUrl); //기존 이미지 삭제
            }
            String imageUrl = awsS3Service.uploadImg(file, "test"); //새로운 이미지 등록
            userProfileRequest.changeImageUrl(imageUrl);
        }
        profile.update(userProfileRequest); //updated by dirty checking

        List<ActivityRequest> activities = userProfileRequest.getActivityRequests();
        List<CareerRequest> careers = userProfileRequest.getCareerRequests();

        //Activity 추가, 수정, 삭제
        List<Long> activityIds = new ArrayList<>();
        for (ActivityRequest activityRequest : activities) {
            if(activityRequest.getId() == null || activityRepository.findById(activityRequest.getId()).isEmpty()) {
                Activity activity = activityRepository.save(
                        Activity.create(
                                activityRequest.getTitle(),
                                activityRequest.getDetails(),
                                activityRequest.getStartDate(),
                                activityRequest.getEndDate(),
                                profile
                        )
                );
                activityIds.add(activity.getId());
            } else {
                Activity activity = activityRepository.findById(activityRequest.getId()).get();
                activity.update(activityRequest); //updated by dirty checking
                activityIds.add(activity.getId());
            }
        }
        //DTO에 포함되지 않은 activity 삭제
        activityRepository.deleteByIdNotInBatch(userId, activityIds);

        //Career 추가, 수정, 삭제
        List<Long> careerIds = new ArrayList<>();
        for (CareerRequest careerRequest : careers) {
            if (careerRequest.getId() == null || careerRepository.findById(careerRequest.getId()).isEmpty()) {
                Career career = careerRepository.save(
                        Career.create(
                                careerRequest.getJob(),
                                careerRequest.getCompany(),
                                careerRequest.getStartDate(),
                                careerRequest.getEndDate(),
                                profile
                        )
                );
                careerIds.add(career.getId());
            } else {
                Career career = careerRepository.findById(careerRequest.getId()).get();
                career.update(careerRequest); //updated by dirty checking
                careerIds.add(career.getId());
            }
        }
        //DTO에 포함되지 않은 career 삭제
        careerRepository.deleteByIdNotInBatch(userId, careerIds);
    }

    @Transactional
    public void withdraw(Long profileId) {
        activityRepository.deleteByProfileId(profileId);
        careerRepository.deleteByProfileId(profileId);
        profileRepository.deleteById(profileId);
    }
}
