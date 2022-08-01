package com.project.sooktoring.repository.custom;

import com.project.sooktoring.domain.QUserProfile;
import com.project.sooktoring.dto.response.MtrFromListResponse;
import com.project.sooktoring.dto.response.MtrFromResponse;
import com.project.sooktoring.dto.response.MtrToListResponse;
import com.project.sooktoring.dto.response.MtrToResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.domain.QMentoring.*;

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
                .leftJoin(mentoring.mentorUserProfile, mentor)
                .where(mentoring.id.eq(mtrId))
                .fetchOne();
    }

    @Override
    public List<MtrFromListResponse> findAllFromDto(Long menteeId) {
        QUserProfile mentor = new QUserProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MtrFromListResponse.class,
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
                .leftJoin(mentoring.mentorUserProfile, mentor)
                .where(mentoring.menteeUserProfile.id.eq(menteeId))
                .orderBy(mentoring.createdDate.desc())
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
                .leftJoin(mentoring.menteeUserProfile, mentee)
                .where(mentoring.id.eq(mtrId))
                .fetchOne();
    }

    @Override
    public List<MtrToListResponse> findAllToDto(Long mentorId) {
        QUserProfile mentee = new QUserProfile("mentee");
        return queryFactory
                .select(
                        Projections.constructor(MtrToListResponse.class,
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
                .leftJoin(mentoring.menteeUserProfile, mentee)
                .where(mentoring.mentorUserProfile.id.eq(mentorId))
                .orderBy(mentoring.createdDate.desc())
                .fetch();
    }
}
