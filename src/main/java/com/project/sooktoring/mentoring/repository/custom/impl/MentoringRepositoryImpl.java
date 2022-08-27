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
import static com.project.sooktoring.profile.domain.QAcademicInfo.*;

@RequiredArgsConstructor
public class MentoringRepositoryImpl implements MentoringRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MentoringFromResponse findFromDtoById(Long mentoringId) {
        QProfile mentorProfile = new QProfile("mentorProfile");
        return queryFactory
                .select(
                        Projections.constructor(MentoringFromResponse.class,
                                mentoring.id,
                                mentorProfile.id,
                                mentorProfile.nickName,
                                mentorProfile.job,
                                mentorProfile.workYear,
                                mentorProfile.imageUrl,
                                mentoring.cat,
                                mentoring.reason,
                                mentoring.talk,
                                mentoring.state
                        )
                )
                .from(mentoring)
                .leftJoin(mentoring.mentorProfile, mentorProfile)
                .where(mentoring.id.eq(mentoringId))
                .fetchOne();
    }

    @Override
    public List<MentoringFromListResponse> findAllFromDto(Long menteeProfileId) {
        QProfile mentorProfile = new QProfile("mentorProfile");
        return queryFactory
                .select(
                        Projections.constructor(MentoringFromListResponse.class,
                                mentoring.id,
                                mentorProfile.id,
                                mentorProfile.nickName,
                                mentorProfile.imageUrl,
                                mentoring.cat,
                                mentoring.state,
                                mentoring.createdDate
                        )
                )
                .from(mentoring)
                .leftJoin(mentoring.mentorProfile, mentorProfile)
                .where(mentoring.menteeProfile.id.eq(menteeProfileId))
                .orderBy(mentoring.createdDate.desc())
                .fetch();
    }

    @Override
    public MentoringToResponse findToDtoById(Long mentoringId) {
        QProfile menteeProfile = new QProfile("menteeProfile");
        return queryFactory
                .select(
                        Projections.constructor(MentoringToResponse.class,
                                mentoring.id,
                                menteeProfile.id,
                                academicInfo.realName,
                                academicInfo.mainMajor,
                                academicInfo.imageUrl,
                                mentoring.cat,
                                mentoring.reason,
                                mentoring.talk,
                                mentoring.state
                        )
                )
                .from(mentoring)
                .leftJoin(mentoring.menteeProfile, menteeProfile)
                .leftJoin(menteeProfile.academicInfo, academicInfo)
                .where(mentoring.id.eq(mentoringId))
                .fetchOne();
    }

    @Override
    public List<MentoringToListResponse> findAllToDto(Long mentorProfileId) {
        QProfile menteeProfile = new QProfile("menteeProfile");
        return queryFactory
                .select(
                        Projections.constructor(MentoringToListResponse.class,
                                mentoring.id,
                                menteeProfile.id,
                                academicInfo.realName,
                                academicInfo.imageUrl,
                                mentoring.cat,
                                mentoring.state,
                                mentoring.createdDate
                        )
                )
                .from(mentoring)
                .leftJoin(mentoring.menteeProfile, menteeProfile)
                .leftJoin(menteeProfile.academicInfo, academicInfo)
                .where(mentoring.mentorProfile.id.eq(mentorProfileId))
                .orderBy(mentoring.createdDate.desc())
                .fetch();
    }
}
