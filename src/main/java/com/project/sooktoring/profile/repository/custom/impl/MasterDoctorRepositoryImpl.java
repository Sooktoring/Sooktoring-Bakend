package com.project.sooktoring.profile.repository.custom.impl;

import com.project.sooktoring.profile.dto.response.MasterDoctorResponse;
import com.project.sooktoring.profile.repository.custom.MasterDoctorRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.profile.domain.QMasterDoctor.masterDoctor;

@RequiredArgsConstructor
public class MasterDoctorRepositoryImpl implements MasterDoctorRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByIdsNotInBatch(Long profileId, List<Long> ids) {
        queryFactory
                .delete(masterDoctor)
                .where(
                        masterDoctor.profile.id.eq(profileId)
                                .and(masterDoctor.id.notIn(ids))
                )
                .execute();
    }

    @Override
    public List<MasterDoctorResponse> findAllDtoByProfileId(Long profileId) {
        return queryFactory
                .select(
                        Projections.constructor(MasterDoctorResponse.class,
                                masterDoctor.id,
                                masterDoctor.degree,
                                masterDoctor.entranceDate,
                                masterDoctor.graduationDate,
                                masterDoctor.isAttend,
                                masterDoctor.univName,
                                masterDoctor.mainMajor
                        )
                )
                .from(masterDoctor)
                .where(masterDoctor.profile.id.eq(profileId))
                .fetch();
    }
}
