package com.project.sooktoring.profile.repository;

import com.project.sooktoring.profile.domain.AcademicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AcademicInfoRepository extends JpaRepository<AcademicInfo, Long> {

    @Query("select ai from AcademicInfo ai where ai.user.id = :userId")
    Optional<AcademicInfo> findByUserId(@Param("userId") Long userId);
}
