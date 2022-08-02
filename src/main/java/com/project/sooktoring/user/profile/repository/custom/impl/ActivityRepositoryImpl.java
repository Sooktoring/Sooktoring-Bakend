package com.project.sooktoring.user.profile.repository.custom.impl;

import com.project.sooktoring.user.profile.repository.custom.ActivityRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.user.profile.domain.QActivity.activity;

@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByIdNotInBatch(Long userId, List<Long> ids) {
        queryFactory
                .delete(activity)
                .where(
                        activity.userProfile.id.eq(userId)
                                .and(activity.id.notIn(ids))
                )
                .execute();
    }
}
