package com.project.sooktoring.profile.repository.custom;

import com.project.sooktoring.profile.dto.response.ActivityResponse;

import java.util.List;
import java.util.Map;

public interface ActivityRepositoryCustom {

    void deleteByIdsNotInBatch(Long profileId, List<Long> ids);
    List<ActivityResponse> findAllDto(Long profileId);
    Map<Long, List<ActivityResponse>> findAllMap();
}
