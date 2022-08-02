package com.project.sooktoring.mentoring.repository.custom;

import com.project.sooktoring.mentoring.dto.response.MtrFromListResponse;
import com.project.sooktoring.mentoring.dto.response.MtrFromResponse;
import com.project.sooktoring.mentoring.dto.response.MtrToListResponse;
import com.project.sooktoring.mentoring.dto.response.MtrToResponse;

import java.util.List;

public interface MentoringRepositoryCustom {

    MtrFromResponse findFromDtoById(Long mtrId);
    List<MtrFromListResponse> findAllFromDto(Long menteeId);
    MtrToResponse findToDtoById(Long mtrId);
    List<MtrToListResponse> findAllToDto(Long mentorId);
}
