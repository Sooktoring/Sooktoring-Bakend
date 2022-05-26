package com.project.sooktoring.repository;

import com.project.sooktoring.dto.response.MtrFromResponse;
import com.project.sooktoring.dto.response.MtrToResponse;

import java.util.List;

public interface MentoringRepositoryCustom {

    MtrFromResponse findFromDtoById(Long mtrId);
    List<MtrFromResponse> findAllFromDto(Long menteeId);
    MtrToResponse findToDtoById(Long mtrId);
    List<MtrToResponse> findAllToDto(Long mentorId);
}
