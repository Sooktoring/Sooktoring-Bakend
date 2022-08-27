package com.project.sooktoring.profile.repository;

import com.project.sooktoring.profile.domain.MasterDoctor;
import com.project.sooktoring.profile.repository.custom.MasterDoctorRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MasterDoctorRepository extends JpaRepository<MasterDoctor, Long>, MasterDoctorRepositoryCustom {

    @Modifying
    @Query("delete from MasterDoctor md where md.profile.id = :profileId")
    void deleteByProfileId(Long profileId);
}
