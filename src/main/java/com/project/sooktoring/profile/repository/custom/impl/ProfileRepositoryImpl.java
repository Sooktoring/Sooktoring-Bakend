package com.project.sooktoring.profile.repository.custom.impl;

import com.project.sooktoring.profile.dto.response.MenteeProfileResponse;
import com.project.sooktoring.profile.dto.response.MentorProfileListResponse;
import com.project.sooktoring.profile.dto.response.MentorProfileResponse;
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
    public List<MentorProfileListResponse> findAllMentorDto() {
        return queryFactory
                .select(
                        Projections.constructor(MentorProfileListResponse.class,
                                profile.id,
                                profile.imageUrl,
                                profile.job,
                                profile.workYear,
                                profile.nickName,
                                profile.isMentor
                        )
                )
                .from(profile)
                .where(profile.isMentor.isTrue())
                .fetch();
    }

    @Override
    public MentorProfileResponse findMentorDtoById(Long profileId) {
        return queryFactory
                .select(
                        Projections.constructor(MentorProfileResponse.class,
                                profile.id,
                                profile.imageUrl,
                                profile.job,
                                profile.workYear,
                                profile.nickName,
                                profile.isMentor
                        )
                )
                .from(profile)
                .where(profile.id.eq(profileId))
                .fetchOne();
    }

    @Override
    public MenteeProfileResponse findMenteeDtoById(Long profileId) {
        return queryFactory
                .select(
                        Projections.constructor(MenteeProfileResponse.class,
                                profile.id,
                                profile.imageUrl,
                                profile.job,
                                profile.workYear,
                                profile.nickName,
                                profile.isMentor
                        )
                )
                .from(profile)
                .where(profile.id.eq(profileId))
                .fetchOne();
    }
}
