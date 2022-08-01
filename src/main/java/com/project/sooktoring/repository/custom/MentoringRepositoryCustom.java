package com.project.sooktoring.repository.custom;

import com.project.sooktoring.dto.response.MtrFromListResponse;
import com.project.sooktoring.dto.response.MtrFromResponse;
import com.project.sooktoring.dto.response.MtrToListResponse;
import com.project.sooktoring.dto.response.MtrToResponse;

import java.util.List;

public interface MentoringRepositoryCustom {

    MtrFromResponse findFromDtoById(Long mtrId);
    List<MtrFromListResponse> findAllFromDto(Long menteeId);
    MtrToResponse findToDtoById(Long mtrId);
    List<MtrToListResponse> findAllToDto(Long mentorId);
}
