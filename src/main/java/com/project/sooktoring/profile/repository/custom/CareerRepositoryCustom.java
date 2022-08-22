package com.project.sooktoring.profile.repository.custom;

import com.project.sooktoring.profile.dto.response.CareerResponse;

import java.util.List;
import java.util.Map;

public interface CareerRepositoryCustom {

    void deleteByIdsNotInBatch(Long profileId, List<Long> ids);
    List<CareerResponse> findAllDto(Long profileId);
    Map<Long, List<CareerResponse>> findAllMap();
}
