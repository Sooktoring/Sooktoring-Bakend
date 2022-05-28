package com.project.sooktoring.repository.custom;

import com.project.sooktoring.domain.QUserProfile;
import com.project.sooktoring.dto.response.MtrFromResponse;
import com.project.sooktoring.dto.response.MtrToResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.domain.QMentoring.*;
import static com.project.sooktoring.domain.QUser.*;

@RequiredArgsConstructor
public class MentoringRepositoryImpl implements MentoringRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MtrFromResponse findFromDtoById(Long mtrId) {
        QUserProfile mentor = new QUserProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MtrFromResponse.class,
                                mentoring.id,
                                mentor.id,
                                user.name,
                                mentor.mainMajor,
                                mentor.job,
                                mentoring.cat,
                                mentoring.reason,
                                mentoring.talk,
                                mentoring.isAccept
                        )
                )
                .from(mentoring)
                .join(mentoring.mentorUserProfile, mentor)
                .join(mentor.user, user)
                .where( mentoring.id.eq(mtrId))
                .fetchOne();
    }

    @Override
    public List<MtrFromResponse> findAllFromDto(Long menteeId) {
        QUserProfile mentor = new QUserProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MtrFromResponse.class,
                                mentoring.id,
                                mentor.id,
                                user.name,
                                mentor.mainMajor,
                                mentor.job,
                                mentoring.cat,
                                mentoring.reason,
                                mentoring.talk,
                                mentoring.isAccept
                        )
                )
                .from(mentoring)
                .join(mentoring.mentorUserProfile, mentor)
                .join(mentor.user, user)
                .where(mentoring.menteeUserProfile.id.eq(menteeId))
                .fetch();
    }

    @Override
    public MtrToResponse findToDtoById(Long mtrId) {
        QUserProfile mentee = new QUserProfile("mentee");
        return queryFactory
                .select(
                        Projections.constructor(MtrToResponse.class,
                                mentoring.id,
                                mentee.id,
                                user.name,
                                mentee.mainMajor,
                                mentoring.cat,
                                mentoring.reason,
                                mentoring.talk,
                                mentoring.isAccept
                        )
                )
                .from(mentoring)
                .join(mentoring.mentorUserProfile, mentee)
                .join(mentee.user, user)
                .where( mentoring.id.eq(mtrId))
                .fetchOne();
    }

    @Override
    public List<MtrToResponse> findAllToDto(Long mentorId) {
        QUserProfile mentee = new QUserProfile("mentee");
        return queryFactory
                .select(
                        Projections.constructor(MtrToResponse.class,
                                mentoring.id,
                                mentee.id,
                                user.name,
                                mentee.mainMajor,
                                mentoring.cat,
                                mentoring.reason,
                                mentoring.talk,
                                mentoring.isAccept
                        )
                )
                .from(mentoring)
                .join(mentoring.menteeUserProfile, mentee)
                .join(mentee.user, user)
                .where(mentoring.mentorUserProfile.id.eq(mentorId))
                .fetch();
    }
}
