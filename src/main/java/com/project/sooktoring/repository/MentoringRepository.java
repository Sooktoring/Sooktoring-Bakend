package com.project.sooktoring.repository;

import com.project.sooktoring.domain.Mentoring;
import com.project.sooktoring.repository.custom.MentoringRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentoringRepository extends JpaRepository<Mentoring, Long>, MentoringRepositoryCustom {

    @Query("select mtr from Mentoring mtr " +
            "where mtr.mentorUserProfile.id = :userId or mtr.menteeUserProfile.id = :userId")
    List<Mentoring> findByUserId(Long userId);

    @Modifying
    @Query("delete from Mentoring mtr where mtr.id in :mtrIds")
    void deleteAllById(List<Long> mtrIds);
}
