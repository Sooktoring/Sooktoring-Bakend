package com.project.sooktoring.mentoring.repository.custom;

import com.project.sooktoring.mentoring.dto.response.*;

import java.util.List;

public interface MentoringRepositoryCustom {

    MentoringFromResponse findFromDtoById(Long mtrId);
    List<MentoringFromListResponse> findAllFromDto(Long menteeId);
    MentoringToResponse findToDtoById(Long mtrId);
    List<MentoringToListResponse> findAllToDto(Long mentorId);
}
