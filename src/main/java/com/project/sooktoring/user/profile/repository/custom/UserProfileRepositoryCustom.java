package com.project.sooktoring.user.profile.repository.custom;

import com.project.sooktoring.user.profile.dto.response.MentorResponse;
import com.project.sooktoring.user.profile.dto.response.UserProfileResponse;

import java.util.List;

public interface UserProfileRepositoryCustom {

    List<MentorResponse> findMentors();
    MentorResponse findMentor(Long mentorId);
    UserProfileResponse findDtoById(Long id);
    List<UserProfileResponse> findAllDto();
}
