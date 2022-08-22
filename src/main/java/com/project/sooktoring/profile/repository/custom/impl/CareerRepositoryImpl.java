package com.project.sooktoring.profile.repository.custom.impl;

import com.project.sooktoring.profile.dto.response.CareerResponse;
import com.project.sooktoring.profile.repository.custom.CareerRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<CareerResponse> findAllDto(Long profileId) {
        return queryFactory
                .select(
                        Projections.constructor(CareerResponse.class,
                                career.id,
                                career.job,
                                career.company,
                                career.startDate,
                                career.endDate
                        )
                )
                .from(career)
                .where(career.profile.id.eq(profileId))
                .orderBy(
                        career.startDate.desc(),
                        career.endDate.desc().nullsFirst(),
                        career.createdDate.desc()
                )
                .fetch();
    }

    @Override
    public Map<Long, List<CareerResponse>> findAllMap() {
        List<Tuple> careerTuple = queryFactory
                .select(
                        Projections.constructor(CareerResponse.class,
                                career.id,
                                career.job,
                                career.company,
                                career.startDate,
                                career.endDate
                        ),
                        career.profile.id
                )
                .from(career)
                .orderBy(
                        career.startDate.desc(),
                        career.endDate.desc().nullsFirst(),
                        career.createdDate.desc()
                )
                .fetch();

        return careerTuple.stream().collect(
                Collectors.groupingBy(
                        t -> t.get(career.profile.id),
                        Collectors.mapping(
                                t -> t.get(0, CareerResponse.class),
                                Collectors.toList()
                        )
                )
        );
    }
}
