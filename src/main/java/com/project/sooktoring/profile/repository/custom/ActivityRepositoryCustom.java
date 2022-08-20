package com.project.sooktoring.profile.repository.custom;

import java.util.List;

public interface ActivityRepositoryCustom {

    void deleteByIdNotInBatch(Long userId, List<Long> ids);
}
