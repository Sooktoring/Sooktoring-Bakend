package com.project.sooktoring.profile.repository.custom.impl;

import com.project.sooktoring.profile.dto.response.ActivityResponse;
import com.project.sooktoring.profile.dto.response.CareerResponse;
import com.project.sooktoring.profile.dto.response.MentorProfileResponse;
import com.project.sooktoring.profile.dto.response.ProfileResponse;
import com.project.sooktoring.profile.repository.custom.ProfileRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.sooktoring.profile.domain.QActivity.activity;
import static com.project.sooktoring.profile.domain.QCareer.career;
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
    public MentorProfileResponse findMentor(Long mentorId) {
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
                .where(profile.id.eq(mentorId), profile.isMentor.isTrue())
                .fetchOne();
    }

    @Override
    public ProfileResponse findDtoById(Long userId) {
        //DTO로 조회
        ProfileResponse userProfileResponse = queryFactory
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
                .where(profile.id.eq(userId))
                .fetchOne();

        List<ActivityResponse> activityResponses = queryFactory
                .select(
                        Projections.constructor(ActivityResponse.class,
                                activity.id,
                                activity.title,
                                activity.details,
                                activity.startDate,
                                activity.endDate
                        )
                )
                .from(activity)
                .where(activity.profile.id.eq(userId))
                .orderBy(
                        activity.startDate.desc(),
                        activity.endDate.desc().nullsFirst(),
                        activity.createdDate.desc()
                )
                .fetch();

        List<CareerResponse> careerResponses = queryFactory
                .select(
                        Projections.constructor(CareerResponse.class,
                                career.id,
                                career.job,
                                career.company,
                                career.startDate,
                                career.endDate
                        )
                )
                .from(career)
                .where(career.profile.id.eq(userId))
                .orderBy(
                        career.startDate.desc(),
                        career.endDate.desc().nullsFirst(),
                        career.createdDate.desc()
                )
                .fetch();

        assert userProfileResponse != null;
        return userProfileResponse.changeList(activityResponses, careerResponses);
    }

    /**
     * 쿼리 3번 나감 (user_profile, activity, career 테이블에 대해)
     */
    @Override
    public List<ProfileResponse> findAllDto() {
        //UserProfile 조회시 DTO 컬렉션으로 조회 -> batch size 설정 의미X
        List<ProfileResponse> userProfileResponses = queryFactory
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
        List<Long> userIds = userProfileResponses.stream().map(ProfileResponse::getId).collect(Collectors.toList());

        List<Tuple> activityResult = queryFactory
                .select(
                        Projections.constructor(ActivityResponse.class,
                                activity.id,
                                activity.title,
                                activity.details,
                                activity.startDate,
                                activity.endDate
                        ),
                        activity.profile.id
                )
                .from(activity)
                .where(activity.profile.id.in(userIds))
                .orderBy(
                        activity.startDate.desc(),
                        activity.endDate.desc().nullsFirst(),
                        activity.createdDate.desc()
                )
                .fetch();
        Map<Long, List<ActivityResponse>> activityResponsesMap = activityResult.stream().collect(
                Collectors.groupingBy(
                        t -> t.get(activity.profile.id),
                        Collectors.mapping(
                                t -> t.get(0, ActivityResponse.class),
                                Collectors.toList()
                        )
                )
        );

        List<Tuple> careerResult = queryFactory
                .select(
                        Projections.constructor(CareerResponse.class,
                                career.id,
                                career.job,
                                career.company,
                                career.startDate,
                                career.endDate
                        ),
                        career.profile.id
                )
                .from(career)
                .where(career.profile.id.in(userIds))
                .orderBy(
                        career.startDate.desc(),
                        career.endDate.desc().nullsFirst(),
                        career.createdDate.desc()
                )
                .fetch();
        Map<Long, List<CareerResponse>> careerResponsesMap = careerResult.stream().collect(
                Collectors.groupingBy(
                        t -> t.get(career.profile.id),
                        Collectors.mapping(
                                t -> t.get(0, CareerResponse.class),
                                Collectors.toList()
                        )
                )
        );

        for (ProfileResponse userProfileResponse : userProfileResponses) {
            Long userId = userProfileResponse.getId();
            userProfileResponse.changeList(
                    activityResponsesMap.get(userId),
                    careerResponsesMap.get(userId)
            );
        }

        return userProfileResponses;
    }
}
