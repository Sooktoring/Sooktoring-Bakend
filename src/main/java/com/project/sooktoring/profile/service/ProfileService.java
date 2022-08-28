package com.project.sooktoring.profile.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.common.utils.S3Uploader;
import com.project.sooktoring.profile.domain.MasterDoctor;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.profile.dto.request.*;
import com.project.sooktoring.profile.dto.response.*;
import com.project.sooktoring.profile.domain.Activity;
import com.project.sooktoring.profile.domain.Career;
import com.project.sooktoring.profile.repository.ActivityRepository;
import com.project.sooktoring.profile.repository.CareerRepository;
import com.project.sooktoring.profile.repository.MasterDoctorRepository;
import com.project.sooktoring.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileUtil profileUtil;
    private final S3Uploader s3Uploader;
    private final ProfileRepository profileRepository;
    private final MasterDoctorRepository masterDoctorRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;

    public MentorProfileResponse getMyMentorProfile() {
        Long profileId = profileUtil.getCurrentProfile().getId();
        return _getMentorProfileResponse(profileId);
    }

    public MentorProfileResponse getMentorProfile(Long profileId) {
        Profile profile = profileUtil.getProfile(profileId);
        if (!profile.getIsMentor()) {
            throw new CustomException(NOT_FOUND_MENTOR);
        }

        return _getMentorProfileResponse(profileId);
    }

    public MenteeProfileResponse getMyMenteeProfile() {
        Long profileId = profileUtil.getCurrentProfile().getId();
        return _getMenteeProfileResponse(profileId);
    }

    public MenteeProfileResponse getMenteeProfile(Long profileId) {
        Profile profile = profileUtil.getProfile(profileId);
        if (profile.getIsMentor()) {
            throw new CustomException(NOT_FOUND_MENTEE);
        }

        return _getMenteeProfileResponse(profileId);
    }

    public List<MentorProfileListResponse> getMentorProfileDtoList() {
        return profileRepository.findAllMentorDto();
    }

    @Transactional
    public void updateMyMentorProfile(MentorProfileRequest mentorProfileRequest, MultipartFile file) {
        Profile profile = profileUtil.getCurrentProfile();
        String imageUrl = s3Uploader.getImageUrl(file, profile.getImageUrl());
        profile.update(imageUrl, mentorProfileRequest.getJob(), mentorProfileRequest.getNickName());
        profile.changeWorkYear(_getWorkYear(mentorProfileRequest.getCareerList())); //연차 계산

        _changeMasterDoctor(mentorProfileRequest.getMasterDoctorList(), profile);
        _changeCareer(mentorProfileRequest.getCareerList(), profile);
    }

    @Transactional
    public void updateMyMenteeProfile(MenteeProfileRequest menteeProfileRequest, MultipartFile file) {
        Profile profile = profileUtil.getCurrentProfile();
        String imageUrl = s3Uploader.getImageUrl(file, profile.getImageUrl());
        profile.update(imageUrl, menteeProfileRequest.getJob(), menteeProfileRequest.getNickName());

        _changeActivity(menteeProfileRequest.getActivityList(), profile);
    }

    public void checkNickName(String nickName) {
        Long profileId = profileUtil.getCurrentProfile().getId();
        Optional<Profile> profileOptional = profileRepository.findByNickName(profileId, nickName);
        if (profileOptional.isPresent()) {
            throw new CustomException(ALREADY_NICKNAME_EXISTS);
        }
    }

    //=== private 메소드 ===

    private MentorProfileResponse _getMentorProfileResponse(Long profileId) {
        MentorProfileResponse mentorProfileResponse = profileRepository.findMentorDtoById(profileId);
        List<MasterDoctorResponse> masterDoctorResponseList = masterDoctorRepository.findAllDtoByProfileId(profileId);
        List<CareerResponse> careerResponseList = careerRepository.findAllDtoByProfileId(profileId);
        mentorProfileResponse.changeList(masterDoctorResponseList, careerResponseList);
        return mentorProfileResponse;
    }

    private MenteeProfileResponse _getMenteeProfileResponse(Long profileId) {
        MenteeProfileResponse menteeProfileResponse = profileRepository.findMenteeDtoById(profileId);
        List<ActivityResponse> activityResponseList = activityRepository.findAllDtoByProfileId(profileId);
        menteeProfileResponse.changeList(activityResponseList);
        return menteeProfileResponse;
    }

    private Long _getWorkYear(List<CareerRequest> careerList) {
        for (CareerRequest careerRequest : careerList) {
            if (careerRequest.getIsWork()) {
                return (long) (YearMonth.now().getYear() - careerRequest.getStartDate().getYear());
            }
        }
        return null;
    }

    private void _changeMasterDoctor(List<MasterDoctorRequest> masterDoctorRequestList, Profile profile) {
        List<Long> masterDoctorIds = new ArrayList<>();

        for (MasterDoctorRequest masterDoctorRequest : masterDoctorRequestList) {
            if (masterDoctorRequest.getMasterDoctorId() == null ||
                    masterDoctorRepository.findById(masterDoctorRequest.getMasterDoctorId()).isEmpty()) {
                MasterDoctor masterDoctor = masterDoctorRepository.save(MasterDoctor.create(masterDoctorRequest, profile));
                masterDoctorIds.add(masterDoctor.getId());
            } else {
                MasterDoctor masterDoctor = masterDoctorRepository.findById(masterDoctorRequest.getMasterDoctorId()).get();
                if (!Objects.equals(masterDoctor.getProfile().getId(), profile.getId())) {
                    throw new CustomException(NOT_FOUND_MASTER_DOCTOR);
                }

                masterDoctor.update(masterDoctorRequest);
                masterDoctorIds.add(masterDoctor.getId());
            }
        }
        //DTO에 포함되지 않은 masterDoctor 삭제
        masterDoctorRepository.deleteByIdsNotInBatch(profile.getId(), masterDoctorIds);
    }

    private void _changeCareer(List<CareerRequest> careerRequestList, Profile profile) {
        List<Long> careerIds = new ArrayList<>();

        for (CareerRequest careerRequest : careerRequestList) {
            if (careerRequest.getCareerId() == null || careerRepository.findById(careerRequest.getCareerId()).isEmpty()) {
                Career career = careerRepository.save(Career.create(careerRequest, profile));
                careerIds.add(career.getId());
            } else {
                Career career = careerRepository.findById(careerRequest.getCareerId()).get();
                if (!Objects.equals(career.getProfile().getId(), profile.getId())) {
                    throw new CustomException(NOT_FOUND_CAREER);
                }

                career.update(careerRequest); //updated by dirty checking
                careerIds.add(career.getId());
            }
        }
        //DTO에 포함되지 않은 career 삭제
        careerRepository.deleteByIdsNotInBatch(profile.getId(), careerIds);
    }

    private void _changeActivity(List<ActivityRequest> activityRequestList, Profile profile) {
        List<Long> activityIds = new ArrayList<>();

        for (ActivityRequest activityRequest : activityRequestList) {
            if(activityRequest.getActivityId() == null || activityRepository.findById(activityRequest.getActivityId()).isEmpty()) {
                Activity activity = activityRepository.save(Activity.create(activityRequest, profile));
                activityIds.add(activity.getId());
            } else {
                Activity activity = activityRepository.findById(activityRequest.getActivityId()).get();
                if (!Objects.equals(activity.getProfile().getId(), profile.getId())) {
                    throw new CustomException(NOT_FOUND_ACTIVITY);
                }

                activity.update(activityRequest); //updated by dirty checking
                activityIds.add(activity.getId());
            }
        }
        //DTO에 포함되지 않은 activity 삭제
        activityRepository.deleteByIdsNotInBatch(profile.getId(), activityIds);
    }
}
