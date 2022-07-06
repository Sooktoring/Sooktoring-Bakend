package com.project.sooktoring.repository.custom;

import com.project.sooktoring.dto.response.MentorResponse;
import com.project.sooktoring.dto.response.UserProfileResponse;

import java.util.List;

public interface UserProfileRepositoryCustom {

    List<MentorResponse> findMentors();
    MentorResponse findMentor(Long mentorId);
    UserProfileResponse findDtoById(Long id);
    List<UserProfileResponse> findAllDto();
}
