package com.project.sooktoring.repository.custom;

import com.project.sooktoring.domain.QUserProfile;
import com.project.sooktoring.dto.response.MtrCardFromResponse;
import com.project.sooktoring.dto.response.MtrCardToResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.domain.QMentoring.*;
import static com.project.sooktoring.domain.QMentoringCard.*;

@RequiredArgsConstructor
public class MentoringCardRepositoryImpl implements MentoringCardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MtrCardFromResponse> findAllFromDto(Long menteeId) {
        QUserProfile mentor = new QUserProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MtrCardFromResponse.class,
                                mentoringCard.id,
                                mentoring.mentorUserProfile.id,
                                mentor.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.menteeUserProfile.id.eq(menteeId))
                .leftJoin(mentoring.mentorUserProfile, mentor)
                .orderBy(mentoringCard.createdDate.desc())
                .fetch();
    }

    @Override
    public MtrCardFromResponse findFromDtoById(Long mtrCardId) {
        QUserProfile mentor = new QUserProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MtrCardFromResponse.class,
                                mentoringCard.id,
                                mentoring.mentorUserProfile.id,
                                mentor.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.id.eq(mtrCardId))
                .leftJoin(mentoring.mentorUserProfile, mentor)
                .fetchOne();
    }

    @Override
    public List<MtrCardToResponse> findAllToDto(Long mentorId) {
        QUserProfile mentee = new QUserProfile("mentee");
        return queryFactory
                .select(
                        Projections.constructor(MtrCardToResponse.class,
                                mentoringCard.id,
                                mentoring.menteeUserProfile.id,
                                mentee.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.mentorUserProfile.id.eq(mentorId))
                .leftJoin(mentoring.menteeUserProfile, mentee)
                .orderBy(mentoringCard.createdDate.desc())
                .fetch();
    }
}
