package com.project.sooktoring.service;

import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.domain.*;
import com.project.sooktoring.dto.request.*;
import com.project.sooktoring.dto.response.UserProfileResponse;
import com.project.sooktoring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.sooktoring.enumerate.MentoringState.*;
import static com.project.sooktoring.enumerate.Role.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileService {

    private final AwsS3Service awsS3Service;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;
    private final MentoringRepository mentoringRepository;

    @Value("${cloud.aws.s3.default.image}")
    private String defaultImageUrl;

    public List<UserProfileResponse> getUserProfiles() {
        return userProfileRepository.findAllDto();
    }

    public UserProfileResponse getUserProfile(Long userId) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userId);
        if (userProfileOptional.isPresent()) {
            return userProfileRepository.findDtoById(userId);
        }
        return null;
    }

    //Activity, Career 추가, 수정, 삭제 한번에 수행
    @Transactional
    public void update(UserProfileRequest userProfileRequest, MultipartFile file, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userId);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            //멘토 -> 멘티 : APPLY -> INVALID, ACCEPT -> END
            if (user.getRole() == ROLE_MENTOR && !userProfileRequest.getIsMentor()) {
                //나에게 온 멘토링 신청내역 APPLY -> INVALID, ACCEPT -> END 로 변경
                List<Mentoring> applyMentoringListToMe = mentoringRepository.findByMentorIdAndState(userId, APPLY);
                for (Mentoring mentoring : applyMentoringListToMe) {
                    mentoring.invalid();
                }
                List<Mentoring> acceptMentoringListToMe = mentoringRepository.findByMentorIdAndState(userId, ACCEPT);
                for (Mentoring mentoring : acceptMentoringListToMe) {
                    mentoring.end();
                }
            }

            //멘티 -> 멘토 : INVALID -> APPLY
            if (user.getRole() == ROLE_MENTEE && userProfileRequest.getIsMentor()) {
                //이전에 멘토 -> 멘티 -> 멘토로 변경하는 경우 INVALID 상태의 나에게 온 멘토링 신청내역 APPLY 로 변경
                List<Mentoring> invalidMentoringListToMe = mentoringRepository.findByMentorIdAndState(userId, INVALID);
                for (Mentoring mentoring : invalidMentoringListToMe) {
                    mentoring.apply();
                }
            }

            user.changeRole(userProfileRequest.getIsMentor()); //User ROLE 업데이트
        }

        if(userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            String originImageUrl = userProfile.getImageUrl();
            if (!file.isEmpty()) {
                if (StringUtils.hasText(originImageUrl) && !originImageUrl.equals(defaultImageUrl)) {
                    awsS3Service.deleteImg(originImageUrl); //기존 이미지 삭제
                }
                String imageUrl = awsS3Service.uploadImg(file, "test"); //새로운 이미지 등록
                userProfileRequest.changeImageUrl(imageUrl);
            }
            userProfile.update(userProfileRequest); //updated by dirty checking

            List<ActivityRequest> activities = userProfileRequest.getActivityRequests();
            List<CareerRequest> careers = userProfileRequest.getCareerRequests();

            //Activity 추가, 수정, 삭제
            List<Long> activityIds = new ArrayList<>();
            for (ActivityRequest activityRequest : activities) {
                if(activityRequest.getId() == null ||
                        activityRepository.findById(activityRequest.getId()).isEmpty()) {
                    Activity activity = activityRepository.save(
                            Activity.create(
                                    activityRequest.getTitle(),
                                    activityRequest.getDetails(),
                                    activityRequest.getStartDate(),
                                    activityRequest.getEndDate(),
                                    userProfile
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
                if (careerRequest.getId() == null ||
                        careerRepository.findById(careerRequest.getId()).isEmpty()) {
                    Career career = careerRepository.save(
                            Career.create(
                                    careerRequest.getJob(),
                                    careerRequest.getCompany(),
                                    careerRequest.getStartDate(),
                                    careerRequest.getEndDate(),
                                    userProfile
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
    }
}
