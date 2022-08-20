package com.project.sooktoring.profile.repository.custom;

import com.project.sooktoring.profile.dto.response.MentorProfileResponse;
import com.project.sooktoring.profile.dto.response.ProfileResponse;

import java.util.List;

public interface ProfileRepositoryCustom {

    List<MentorProfileResponse> findMentors();
    MentorProfileResponse findMentor(Long mentorId);
    ProfileResponse findDtoById(Long id);
    List<ProfileResponse> findAllDto();
}
