package com.project.sooktoring.mentoring.repository.custom;

import com.project.sooktoring.mentoring.dto.response.MtrCardFromResponse;
import com.project.sooktoring.mentoring.dto.response.MtrCardToResponse;

import java.util.List;

public interface MentoringCardRepositoryCustom {

    List<MtrCardFromResponse> findAllFromDto(Long menteeId);
    MtrCardFromResponse findFromDtoById(Long mtrCardId);
    List<MtrCardToResponse> findAllToDto(Long mentorId);
}
