package com.project.sooktoring.club.repository.custom.impl;

import com.project.sooktoring.club.dto.response.ClubListResponse;
import com.project.sooktoring.club.dto.response.ClubRecruitListResponse;
import com.project.sooktoring.club.enumerate.ClubKind;
import com.project.sooktoring.club.repository.custom.ClubRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.club.domain.QClub.*;

@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ClubListResponse> findAllByNewOrder(ClubKind kind) {
        return queryFactory
                .select(
                        Projections.constructor(ClubListResponse.class,
                                club.id,
                                club.logoUrl,
                                club.name,
                                club.desc,
                                club.recruitField
                        )
                )
                .from(club)
                .where(club.kind.eq(kind))
                .orderBy(club.createdDate.desc())
                .fetch();
    }

    @Override
    public List<ClubRecruitListResponse> findAllRecruitByNewOrder() {
        return queryFactory
                .select(
                        Projections.constructor(ClubRecruitListResponse.class,
                                club.id,
                                club.logoUrl,
                                club.name,
                                club.desc,
                                club.recruitField
                        )
                )
                .from(club)
                .where(club.isRecruit.isTrue())
                .orderBy(club.createdDate.desc())
                .fetch();
    }
}
