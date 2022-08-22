package com.project.sooktoring.profile.repository.custom.impl;

import com.project.sooktoring.profile.dto.response.ActivityResponse;
import com.project.sooktoring.profile.repository.custom.ActivityRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<ActivityResponse> findAllDto(Long profileId) {
        return queryFactory
                .select(
                        Projections.constructor(ActivityResponse.class,
                                activity.id,
                                activity.title,
                                activity.details,
                                activity.startDate,
                                activity.endDate
                        )
                )
                .from(activity)
                .where(activity.profile.id.eq(profileId))
                .orderBy(
                        activity.startDate.desc(),
                        activity.endDate.desc().nullsFirst(),
                        activity.createdDate.desc()
                )
                .fetch();
    }

    @Override
    public Map<Long, List<ActivityResponse>> findAllMap() {
        List<Tuple> activityTuple = queryFactory
                .select(
                        Projections.constructor(ActivityResponse.class,
                                activity.id,
                                activity.title,
                                activity.details,
                                activity.startDate,
                                activity.endDate
                        ),
                        activity.profile.id
                )
                .from(activity)
                .orderBy(
                        activity.startDate.desc(),
                        activity.endDate.desc().nullsFirst(),
                        activity.createdDate.desc()
                )
                .fetch();

        return activityTuple.stream().collect(
                Collectors.groupingBy(
                        t -> t.get(activity.profile.id),
                        Collectors.mapping(
                                t -> t.get(0, ActivityResponse.class),
                                Collectors.toList()
                        )
                )
        );
    }
}
