package com.project.sooktoring.mentoring.repository.custom;

import com.project.sooktoring.mentoring.dto.response.MentoringCardFromResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringCardToResponse;

import java.util.List;

public interface MentoringCardRepositoryCustom {

    List<MentoringCardFromResponse> findAllFromDto(Long menteeId);
    MentoringCardFromResponse findFromDtoById(Long mtrCardId);
    List<MentoringCardToResponse> findAllToDto(Long mentorId);
}
