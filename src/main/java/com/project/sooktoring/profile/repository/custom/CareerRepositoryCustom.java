package com.project.sooktoring.profile.repository.custom;

import com.project.sooktoring.profile.dto.response.CareerResponse;

import java.util.List;

public interface CareerRepositoryCustom {

    void deleteByIdsNotInBatch(Long profileId, List<Long> ids);
    List<CareerResponse> findAllDtoByProfileId(Long profileId);
}
