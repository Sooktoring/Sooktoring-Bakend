package com.project.sooktoring.mentoring.repository.custom.impl;

import com.project.sooktoring.profile.domain.QProfile;
import com.project.sooktoring.mentoring.dto.response.MentoringFromListResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringFromResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringToListResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringToResponse;
import com.project.sooktoring.mentoring.repository.custom.MentoringRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.mentoring.domain.QMentoring.mentoring;

@RequiredArgsConstructor
public class MentoringRepositoryImpl implements MentoringRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MentoringFromResponse findFromDtoById(Long mtrId) {
        QProfile mentor = new QProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MentoringFromResponse.class,
                                mentoring.id,
                                mentor.id,
                                mentor.realName,
                                mentor.mainMajor,
                                mentor.job,
                                mentor.imageUrl,
                                mentoring.cat,
                                mentoring.reason,
                                mentoring.talk,
                                mentoring.state
                        )
                )
                .from(mentoring)
                .leftJoin(mentoring.mentorProfile, mentor)
                .where(mentoring.id.eq(mtrId))
                .fetchOne();
    }

    @Override
    public List<MentoringFromListResponse> findAllFromDto(Long menteeId) {
        QProfile mentor = new QProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MentoringFromListResponse.class,
                                mentoring.id,
                                mentor.id,
                                mentor.realName,
                                mentor.imageUrl,
                                mentoring.cat,
                                mentoring.state,
                                mentoring.createdDate
                        )
                )
                .from(mentoring)
                .leftJoin(mentoring.mentorProfile, mentor)
                .where(mentoring.menteeProfile.id.eq(menteeId))
                .orderBy(mentoring.createdDate.desc())
                .fetch();
    }

    @Override
    public MentoringToResponse findToDtoById(Long mtrId) {
        QProfile mentee = new QProfile("mentee");
        return queryFactory
                .select(
                        Projections.constructor(MentoringToResponse.class,
                                mentoring.id,
                                mentee.id,
                                mentee.realName,
                                mentee.mainMajor,
                                mentee.imageUrl,
                                mentoring.cat,
                                mentoring.reason,
                                mentoring.talk,
                                mentoring.state
                        )
                )
                .from(mentoring)
                .leftJoin(mentoring.menteeProfile, mentee)
                .where(mentoring.id.eq(mtrId))
                .fetchOne();
    }

    @Override
    public List<MentoringToListResponse> findAllToDto(Long mentorId) {
        QProfile mentee = new QProfile("mentee");
        return queryFactory
                .select(
                        Projections.constructor(MentoringToListResponse.class,
                                mentoring.id,
                                mentee.id,
                                mentee.realName,
                                mentee.imageUrl,
                                mentoring.cat,
                                mentoring.state,
                                mentoring.createdDate
                        )
                )
                .from(mentoring)
                .leftJoin(mentoring.menteeProfile, mentee)
                .where(mentoring.mentorProfile.id.eq(mentorId))
                .orderBy(mentoring.createdDate.desc())
                .fetch();
    }
}
