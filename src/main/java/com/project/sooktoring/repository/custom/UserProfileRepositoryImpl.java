package com.project.sooktoring.repository.custom;

import com.project.sooktoring.dto.response.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.sooktoring.domain.QActivity.*;
import static com.project.sooktoring.domain.QCareer.*;
import static com.project.sooktoring.domain.QUserProfile.*;

@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MentorResponse> findMentors() {
        return queryFactory
                .select(
                        Projections.constructor(MentorResponse.class,
                                userProfile.id,
                                userProfile.realName,
                                userProfile.job,
                                userProfile.workYear,
                                userProfile.mainMajor,
                                userProfile.imageUrl
                        )
                )
                .from(userProfile)
                .where(userProfile.isMentor.eq(true))
                .fetch();
    }

    @Override
    public MentorResponse findMentor(Long mentorId) {
        return queryFactory
                .select(
                        Projections.constructor(MentorResponse.class,
                                userProfile.id,
                                userProfile.realName,
                                userProfile.job,
                                userProfile.workYear,
                                userProfile.mainMajor,
                                userProfile.imageUrl
                        )
                )
                .from(userProfile)
                .where(userProfile.id.eq(mentorId), userProfile.isMentor.eq(true))
                .fetchOne();
    }

    @Override
    public UserProfileResponse findDtoById(Long userId) {
        //DTO로 조회
        UserProfileResponse userProfileResponse = queryFactory
                .select(
                        Projections.constructor(UserProfileResponse.class,
                                userProfile.id,
                                userProfile.realName,
                                userProfile.mainMajor,
                                userProfile.doubleMajor,
                                userProfile.minor,
                                userProfile.entranceDate,
                                userProfile.gradDate,
                                userProfile.job,
                                userProfile.workYear,
                                userProfile.isMentor,
                                userProfile.imageUrl
                        )
                )
                .from(userProfile)
                .where(userProfile.id.eq(userId))
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
                .where(activity.userProfile.id.eq(userId))
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
                .where(career.userProfile.id.eq(userId))
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
    public List<UserProfileResponse> findAllDto() {
        //UserProfile 조회시 DTO 컬렉션으로 조회 -> batch size 설정 의미X
        List<UserProfileResponse> userProfileResponses = queryFactory
                .select(
                        Projections.constructor(UserProfileResponse.class,
                                userProfile.id,
                                userProfile.realName,
                                userProfile.mainMajor,
                                userProfile.doubleMajor,
                                userProfile.minor,
                                userProfile.entranceDate,
                                userProfile.gradDate,
                                userProfile.job,
                                userProfile.workYear,
                                userProfile.isMentor,
                                userProfile.imageUrl
                        )
                )
                .from(userProfile)
                .fetch();
        List<Long> userIds = userProfileResponses.stream().map(UserProfileResponse::getId).collect(Collectors.toList());

        List<Tuple> activityResult = queryFactory
                .select(
                        Projections.constructor(ActivityResponse.class,
                                activity.id,
                                activity.title,
                                activity.details,
                                activity.startDate,
                                activity.endDate
                        ),
                        activity.userProfile.id
                )
                .from(activity)
                .where(activity.userProfile.id.in(userIds))
                .orderBy(
                        activity.startDate.desc(),
                        activity.endDate.desc().nullsFirst(),
                        activity.createdDate.desc()
                )
                .fetch();
        Map<Long, List<ActivityResponse>> activityResponsesMap = activityResult.stream().collect(
                Collectors.groupingBy(
                        t -> t.get(activity.userProfile.id),
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
                        career.userProfile.id
                )
                .from(career)
                .where(career.userProfile.id.in(userIds))
                .orderBy(
                        career.startDate.desc(),
                        career.endDate.desc().nullsFirst(),
                        career.createdDate.desc()
                )
                .fetch();
        Map<Long, List<CareerResponse>> careerResponsesMap = careerResult.stream().collect(
                Collectors.groupingBy(
                        t -> t.get(career.userProfile.id),
                        Collectors.mapping(
                                t -> t.get(0, CareerResponse.class),
                                Collectors.toList()
                        )
                )
        );

        for (UserProfileResponse userProfileResponse : userProfileResponses) {
            Long userId = userProfileResponse.getId();
            userProfileResponse.changeList(
                    activityResponsesMap.get(userId),
                    careerResponsesMap.get(userId)
            );
        }

        return userProfileResponses;
    }
}
