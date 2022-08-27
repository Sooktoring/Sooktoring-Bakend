package com.project.sooktoring.profile.repository.custom.impl;

import com.project.sooktoring.profile.dto.response.ActivityResponse;
import com.project.sooktoring.profile.repository.custom.ActivityRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.profile.domain.QActivity.activity;

@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByIdsNotInBatch(Long profileId, List<Long> ids) {
        queryFactory
                .delete(activity)
                .where(
                        activity.profile.id.eq(profileId)
                                .and(activity.id.notIn(ids))
                )
                .execute();
    }

    @Override
    public List<ActivityResponse> findAllDtoByProfileId(Long profileId) {
        return queryFactory
                .select(
                        Projections.constructor(ActivityResponse.class,
                                activity.id,
                                activity.title,
                                activity.details,
                                activity.startDate,
                                activity.endDate,
                                activity.isActive
                        )
                )
                .from(activity)
                .where(activity.profile.id.eq(profileId))
                .fetch();
    }
}
