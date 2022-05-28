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

    @Transactional
    public void save(UserProfileRequest userProfileRequest, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.changeRole(userProfileRequest.getIsMentor()); //User ROLE 업데이트

            UserProfile userProfile = UserProfile.create(userProfileRequest, user);
            userProfileRepository.save(userProfile);

            for (ActivityRequest activityRequest : userProfileRequest.getActivityRequests()) {
                activityRepository.save(
                        Activity.create(
                                activityRequest.getTitle(),
                                activityRequest.getDetails(),
                                activityRequest.getStartDate(),
                                activityRequest.getEndDate(),
                                userProfile
                        )
                );
            }
            for (CareerRequest careerRequest : userProfileRequest.getCareerRequests()) {
                careerRepository.save(
                        Career.create(
                                careerRequest.getJob(),
                                careerRequest.getCompany(),
                                careerRequest.getStartDate(),
                                careerRequest.getEndDate(),
                                userProfile
                        )
                );
            }
        }
    }

    public UserProfileResponse getUserProfile(Long userId) {
        return userProfileRepository.findDtoById(userId);
    }

    public List<UserProfileResponse> getUserProfiles() {
        return userProfileRepository.findAllDto();
    }

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

            for (ActivityRequest activityRequest : activities) {
                if(activityRequest.getId() == null |
                        activityRepository.findById(activityRequest.getId()).isEmpty()) {
                    activityRepository.save(
                            Activity.create(
                                    activityRequest.getTitle(),
                                    activityRequest.getDetails(),
                                    activityRequest.getStartDate(),
                                    activityRequest.getEndDate(),
                                    userProfile
                            )
                    );
                } else {
                    Activity activity = activityRepository.findById(activityRequest.getId()).get();
                    activity.update(activityRequest); //updated by dirty checking
                }
            }

            for (CareerRequest careerRequest : careers) {
                if (careerRequest.getId() == null |
                        careerRepository.findById(careerRequest.getId()).isEmpty()) {
                    careerRepository.save(
                            Career.create(
                                    careerRequest.getJob(),
                                    careerRequest.getCompany(),
                                    careerRequest.getStartDate(),
                                    careerRequest.getEndDate(),
                                    userProfile
                            )
                    );
                } else {
                    Career career = careerRepository.findById(careerRequest.getId()).get();
                    career.update(careerRequest); //updated by dirty checking
                }
            }
        }
    }
}
