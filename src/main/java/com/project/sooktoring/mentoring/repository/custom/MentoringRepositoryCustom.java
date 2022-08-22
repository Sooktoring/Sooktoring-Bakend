package com.project.sooktoring.mentoring.repository.custom;

import com.project.sooktoring.mentoring.dto.response.*;

import java.util.List;

public interface MentoringRepositoryCustom {

    MentoringFromResponse findFromDtoById(Long mentoringId);
    List<MentoringFromListResponse> findAllFromDto(Long menteeProfileId);
    MentoringToResponse findToDtoById(Long mentoringId);
    List<MentoringToListResponse> findAllToDto(Long mentorProfileId);
}
