package com.project.sooktoring.repository.custom;

import com.project.sooktoring.dto.response.MtrCardFromResponse;
import com.project.sooktoring.dto.response.MtrCardToResponse;

import java.util.List;

public interface MentoringCardRepositoryCustom {

    List<MtrCardFromResponse> findAllFromDto(Long menteeId);
    MtrCardFromResponse findFromDtoById(Long mtrCardId);
    List<MtrCardToResponse> findAllToDto(Long mentorId);
}
