package com.project.sooktoring.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.domain.QActivity.*;

@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByIdNotInBatch(Long userId, List<Long> ids) {
        long execute = queryFactory
                .delete(activity)
                .where(
                        activity.userProfile.id.eq(userId)
                                .and(activity.id.notIn(ids))
                )
                .execute();
    }
}
