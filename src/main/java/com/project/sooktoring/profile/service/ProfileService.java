package com.project.sooktoring.profile.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.service.AwsS3Service;
import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.common.utils.UserUtil;
import com.project.sooktoring.mentoring.service.MentoringService;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.profile.dto.response.ActivityResponse;
import com.project.sooktoring.profile.dto.response.CareerResponse;
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
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;
    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;
    private final MentoringService mentoringService;
    private final AwsS3Service awsS3Service;

    @Value("${cloud.aws.s3.default.image}")
    private String defaultImageUrl;

    public List<ProfileResponse> getProfileDtoList() {
        List<ProfileResponse> profileResponseList = profileRepository.findAllDto();
        Map<Long, List<ActivityResponse>> activityResponseListMap = activityRepository.findAllMap();
        Map<Long, List<CareerResponse>> careerResponseListMap = careerRepository.findAllMap();
        _setActivityAndCareer(profileResponseList, activityResponseListMap, careerResponseListMap);

        return profileResponseList;
    }

    public ProfileResponse getProfileDto() {
        Long profileId = profileUtil.getCurrentProfile().getId();
        return _getProfileResponse(profileId);
    }

    public ProfileResponse getProfileDto(Long profileId) {
        profileUtil.getProfile(profileId);
        return _getProfileResponse(profileId);
    }

    public List<MentorProfileResponse> getMentorProfileDtoList() {
        return profileRepository.findMentors();
    }

    public MentorProfileResponse getMentorProfileDto(Long profileId) {
        Profile profile = profileUtil.getProfile(profileId);
        if (profile.getIsMentor()) {
            return profileRepository.findMentor(profileId);
        }
        throw new CustomException(NOT_FOUND_MENTOR);
    }

    @Transactional
    public void withdraw(Long profileId) {
        activityRepository.deleteByProfileId(profileId);
        careerRepository.deleteByProfileId(profileId);
        profileRepository.deleteById(profileId);
    }

    //Activity, Career 추가, 수정, 삭제 한번에 수행
    @Transactional
    public ProfileResponse update(ProfileRequest profileRequest, MultipartFile file) {
        User user = userUtil.getCurrentUser();
        Profile profile = profileUtil.getCurrentProfile();

        mentoringService.changeStateByRole(profileRequest.getIsMentor(), profile); //Role 변경에 따른 멘토링 상태 변경
        user.changeRole(profileRequest.getIsMentor()); //User ROLE 업데이트

        profileRequest.changeImageUrl(_getImageUrl(file, profile.getImageUrl())); //file 저장 후 이미지 url 반환
        profile.update(profileRequest); //updated by dirty checking

        _changeActivity(profileRequest, profile); //Activity 추가, 수정, 삭제
        _changeCareer(profileRequest, profile); //Career 추가, 수정, 삭제

        return _getProfileResponse(profile.getId());
    }

    //=== private 메소드 ===

    private ProfileResponse _getProfileResponse(Long profileId) {
        ProfileResponse profileResponse = profileRepository.findDtoById(profileId);
        profileResponse.changeList(
                activityRepository.findAllDto(profileId),
                careerRepository.findAllDto(profileId)
        );
        return profileResponse;
    }

    private void _setActivityAndCareer(List<ProfileResponse> profileResponseList,
                                       Map<Long, List<ActivityResponse>> activityResponseListMap, Map<Long, List<CareerResponse>> careerResponseListMap) {
        for (ProfileResponse profileResponse : profileResponseList) {
            Long profileId = profileResponse.getId();
            profileResponse.changeList(
                    activityResponseListMap.get(profileId),
                    careerResponseListMap.get(profileId)
            );
        }
    }

    private String _getImageUrl(MultipartFile file, String originImageUrl) {
        if (file != null && !file.isEmpty()) {
            if (StringUtils.hasText(originImageUrl) && !originImageUrl.equals(defaultImageUrl)) {
                awsS3Service.deleteImg(originImageUrl); //기존 이미지 삭제
            }
            return awsS3Service.uploadImg(file, "test"); //새로운 이미지 등록 & 해당 이미지 url 반환
        }
        return originImageUrl;
    }

    private void _changeActivity(ProfileRequest profileRequest, Profile profile) {
        List<ActivityRequest> activities = profileRequest.getActivityRequests();
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
        activityRepository.deleteByIdsNotInBatch(profile.getId(), activityIds);
    }

    private void _changeCareer(ProfileRequest profileRequest, Profile profile) {
        List<CareerRequest> careers = profileRequest.getCareerRequests();
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
        careerRepository.deleteByIdsNotInBatch(profile.getId(), careerIds);
    }
}
