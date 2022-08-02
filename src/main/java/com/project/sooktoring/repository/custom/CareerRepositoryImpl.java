package com.project.sooktoring.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.domain.QCareer.*;

@RequiredArgsConstructor
public class CareerRepositoryImpl implements CareerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByIdNotInBatch(Long userId, List<Long> ids) {
        queryFactory
                .delete(career)
                .where(
                        career.userProfile.id.eq(userId)
                                .and(career.id.notIn(ids))
                )
                .execute();
    }
}
