package com.project.sooktoring.profile.repository.custom;

import java.util.List;

public interface CareerRepositoryCustom {

    void deleteByIdNotInBatch(Long userId, List<Long> ids);
}
