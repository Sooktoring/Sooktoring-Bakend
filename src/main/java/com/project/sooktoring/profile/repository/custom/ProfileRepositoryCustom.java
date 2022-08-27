package com.project.sooktoring.profile.repository.custom;

import com.project.sooktoring.profile.dto.response.MenteeProfileResponse;
import com.project.sooktoring.profile.dto.response.MentorProfileListResponse;
import com.project.sooktoring.profile.dto.response.MentorProfileResponse;

import java.util.List;

public interface ProfileRepositoryCustom {

    List<MentorProfileListResponse> findAllMentorDto();
    MentorProfileResponse findMentorDtoById(Long profileId);
    MenteeProfileResponse findMenteeDtoById(Long profileId);
}
