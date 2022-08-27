package com.project.sooktoring.profile.repository.custom;

import com.project.sooktoring.profile.dto.response.MasterDoctorResponse;

import java.util.List;

public interface MasterDoctorRepositoryCustom {

    void deleteByIdsNotInBatch(Long profileId, List<Long> ids);
    List<MasterDoctorResponse> findAllDtoByProfileId(Long profileId);
}
