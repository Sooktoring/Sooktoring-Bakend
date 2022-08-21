package com.project.sooktoring.profile.repository.custom.impl;

import com.project.sooktoring.profile.dto.response.MentorProfileResponse;
import com.project.sooktoring.profile.dto.response.ProfileResponse;
import com.project.sooktoring.profile.repository.custom.ProfileRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import static com.project.sooktoring.profile.domain.QProfile.profile;

@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MentorProfileResponse> findMentors() {
        return queryFactory
                .select(
                        Projections.constructor(MentorProfileResponse.class,
                                profile.id,
                                profile.realName,
                                profile.job,
                                profile.workYear,
                                profile.mainMajor,
                                profile.imageUrl
                        )
                )
                .from(profile)
                .where(profile.isMentor.isTrue())
                .fetch();
    }

    @Override
    public MentorProfileResponse findMentor(Long profileId) {
        return queryFactory
                .select(
                        Projections.constructor(MentorProfileResponse.class,
                                profile.id,
                                profile.realName,
                                profile.job,
                                profile.workYear,
                                profile.mainMajor,
                                profile.imageUrl
                        )
                )
                .from(profile)
                .where(profile.id.eq(profileId), profile.isMentor.isTrue())
                .fetchOne();
    }

    @Override
    public ProfileResponse findDtoById(Long profileId) {
        //DTO로 조회
        return queryFactory
                .select(
                        Projections.constructor(ProfileResponse.class,
                                profile.id,
                                profile.realName,
                                profile.mainMajor,
                                profile.doubleMajor,
                                profile.minor,
                                profile.entranceDate,
                                profile.graduationDate,
                                profile.job,
                                profile.workYear,
                                profile.isMentor,
                                profile.imageUrl
                        )
                )
                .from(profile)
                .where(profile.id.eq(profileId))
                .fetchOne();
    }

    @Override
    public List<ProfileResponse> findAllDto() {
        return queryFactory
                .select(
                        Projections.constructor(ProfileResponse.class,
                                profile.id,
                                profile.realName,
                                profile.mainMajor,
                                profile.doubleMajor,
                                profile.minor,
                                profile.entranceDate,
                                profile.graduationDate,
                                profile.job,
                                profile.workYear,
                                profile.isMentor,
                                profile.imageUrl
                        )
                )
                .from(profile)
                .fetch();
    }
}
