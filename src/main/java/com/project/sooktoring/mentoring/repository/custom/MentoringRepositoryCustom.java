package com.project.sooktoring.mentoring.repository.custom;

import com.project.sooktoring.mentoring.dto.response.*;

import java.util.List;

public interface MentoringRepositoryCustom {

    MtrFromResponse findFromDtoById(Long mtrId);
    List<MtrFromListResponse> findAllFromDto(Long menteeId);
    MtrToResponse findToDtoById(Long mtrId);
    List<MtrToListResponse> findAllToDto(Long mentorId);
}
