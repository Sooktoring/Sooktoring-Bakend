package com.project.sooktoring.profile.repository.custom.impl;

import com.project.sooktoring.profile.dto.response.CareerResponse;
import com.project.sooktoring.profile.repository.custom.CareerRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.profile.domain.QCareer.career;

@RequiredArgsConstructor
public class CareerRepositoryImpl implements CareerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByIdsNotInBatch(Long profileId, List<Long> ids) {
        queryFactory
                .delete(career)
                .where(
                        career.profile.id.eq(profileId)
                                .and(career.id.notIn(ids))
                )
                .execute();
    }

    @Override
    public List<CareerResponse> findAllDtoByProfileId(Long profileId) {
        return queryFactory
                .select(
                        Projections.constructor(CareerResponse.class,
                                career.id,
                                career.job,
                                career.position,
                                career.startDate,
                                career.endDate,
                                career.isWork
                        )
                )
                .from(career)
                .where(career.profile.id.eq(profileId))
                .fetch();
    }
}
