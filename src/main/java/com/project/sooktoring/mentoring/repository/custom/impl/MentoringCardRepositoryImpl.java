package com.project.sooktoring.mentoring.repository.custom.impl;

import com.project.sooktoring.profile.domain.QProfile;
import com.project.sooktoring.mentoring.dto.response.MentoringCardFromResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringCardToResponse;
import com.project.sooktoring.mentoring.repository.custom.MentoringCardRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.mentoring.domain.QMentoring.mentoring;
import static com.project.sooktoring.mentoring.domain.QMentoringCard.mentoringCard;

@RequiredArgsConstructor
public class MentoringCardRepositoryImpl implements MentoringCardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MentoringCardFromResponse> findAllFromDto(Long menteeId) {
        QProfile mentor = new QProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MentoringCardFromResponse.class,
                                mentoringCard.id,
                                mentoring.mentorProfile.id,
                                mentor.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.menteeProfile.id.eq(menteeId))
                .leftJoin(mentoring.mentorProfile, mentor)
                .orderBy(mentoringCard.createdDate.desc())
                .fetch();
    }

    @Override
    public MentoringCardFromResponse findFromDtoById(Long mtrCardId) {
        QProfile mentor = new QProfile("mentor");
        return queryFactory
                .select(
                        Projections.constructor(MentoringCardFromResponse.class,
                                mentoringCard.id,
                                mentoring.mentorProfile.id,
                                mentor.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.id.eq(mtrCardId))
                .leftJoin(mentoring.mentorProfile, mentor)
                .fetchOne();
    }

    @Override
    public List<MentoringCardToResponse> findAllToDto(Long mentorId) {
        QProfile mentee = new QProfile("mentee");
        return queryFactory
                .select(
                        Projections.constructor(MentoringCardToResponse.class,
                                mentoringCard.id,
                                mentoring.menteeProfile.id,
                                mentee.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.mentorProfile.id.eq(mentorId))
                .leftJoin(mentoring.menteeProfile, mentee)
                .orderBy(mentoringCard.createdDate.desc())
                .fetch();
    }
}
