package com.project.sooktoring.service;

import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.domain.*;
import com.project.sooktoring.dto.request.*;
import com.project.sooktoring.dto.response.UserProfileResponse;
import com.project.sooktoring.repository.ActivityRepository;
import com.project.sooktoring.repository.CareerRepository;
import com.project.sooktoring.repository.UserProfileRepository;
import com.project.sooktoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ActivityRepository activityRepository;
    private final CareerRepository careerRepository;

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
    public void update(UserProfileRequest userProfileRequest, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userId);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.changeRole(userProfileRequest.getIsMentor()); //User ROLE 업데이트
        }

        if(userProfileOptional.isPresent()) {
            List<ActivityRequest> activities = userProfileRequest.getActivityRequests();
            List<CareerRequest> careers = userProfileRequest.getCareerRequests();

            UserProfile userProfile = userProfileOptional.get();
            userProfile.update(userProfileRequest); //updated by dirty checking

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
