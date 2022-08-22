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
    public List<MentoringCardFromResponse> findAllFromDto(Long menteeProfileId) {
        QProfile mentorProfile = new QProfile("mentorProfile");
        return queryFactory
                .select(
                        Projections.constructor(MentoringCardFromResponse.class,
                                mentoringCard.id,
                                mentoring.mentorProfile.id,
                                mentorProfile.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.menteeProfile.id.eq(menteeProfileId))
                .leftJoin(mentoring.mentorProfile, mentorProfile)
                .orderBy(mentoringCard.createdDate.desc())
                .fetch();
    }

    @Override
    public MentoringCardFromResponse findFromDtoById(Long mentoringCardId) {
        QProfile mentorProfile = new QProfile("mentorProfile");
        return queryFactory
                .select(
                        Projections.constructor(MentoringCardFromResponse.class,
                                mentoringCard.id,
                                mentoring.mentorProfile.id,
                                mentorProfile.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.id.eq(mentoringCardId))
                .leftJoin(mentoring.mentorProfile, mentorProfile)
                .fetchOne();
    }

    @Override
    public List<MentoringCardToResponse> findAllToDto(Long mentorProfileId) {
        QProfile menteeProfile = new QProfile("menteeProfile");
        return queryFactory
                .select(
                        Projections.constructor(MentoringCardToResponse.class,
                                mentoringCard.id,
                                mentoring.menteeProfile.id,
                                menteeProfile.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.mentorProfile.id.eq(mentorProfileId))
                .leftJoin(mentoring.menteeProfile, menteeProfile)
                .orderBy(mentoringCard.createdDate.desc())
                .fetch();
    }

    @Override
    public MentoringCardToResponse findToDtoById(Long mentoringCardId) {
        QProfile menteeProfile = new QProfile("menteeProfile");
        return queryFactory
                .select(
                        Projections.constructor(MentoringCardToResponse.class,
                                mentoringCard.id,
                                mentoring.menteeProfile.id,
                                menteeProfile.imageUrl,
                                mentoringCard.title,
                                mentoringCard.content
                        )
                )
                .from(mentoringCard)
                .join(mentoringCard.mentoring, mentoring)
                .on(mentoring.id.eq(mentoringCardId))
                .leftJoin(mentoring.menteeProfile, menteeProfile)
                .fetchOne();
    }
}
