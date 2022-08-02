package com.project.sooktoring.mentoring.repository;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.enumerate.MentoringCat;
import com.project.sooktoring.mentoring.enumerate.MentoringState;
import com.project.sooktoring.mentoring.repository.custom.MentoringRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MentoringRepository extends JpaRepository<Mentoring, Long>, MentoringRepositoryCustom {

    @Modifying
    @Query("update Mentoring mtr set mtr.mentorUserProfile.id = null where mtr.mentorUserProfile.id = :userId")
    void updateMentorByUserId(Long userId);

    @Modifying
    @Query("update Mentoring mtr set mtr.menteeUserProfile.id = null where mtr.menteeUserProfile.id = :userId")
    void updateMenteeByUserId(Long userId);

    @Query("select mtr from Mentoring mtr where mtr.mentorUserProfile.id = :mentorId and mtr.cat = :cat")
    Optional<Mentoring> findByMentorIdAndCat(Long mentorId, MentoringCat cat);

    @Query("select mtr from Mentoring mtr where mtr.mentorUserProfile.id = :mentorId and mtr.state = :state")
    List<Mentoring> findByMentorIdAndState(Long mentorId, MentoringState state);

    @Query("select mtr from Mentoring mtr where mtr.mentorUserProfile.id = :mentorId")
    List<Mentoring> findByMentorId(Long mentorId);

    @Query("select mtr from Mentoring mtr where mtr.menteeUserProfile.id = :menteeId")
    List<Mentoring> findByMenteeId(Long menteeId);
}