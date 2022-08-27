package com.project.sooktoring.profile.repository.custom;

import com.project.sooktoring.profile.dto.response.ActivityResponse;

import java.util.List;

public interface ActivityRepositoryCustom {

    void deleteByIdsNotInBatch(Long profileId, List<Long> ids);
    List<ActivityResponse> findAllDtoByProfileId(Long profileId);
}
