package com.project.sooktoring.mentoring.repository;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.enumerate.MentoringCat;
import com.project.sooktoring.mentoring.enumerate.MentoringState;
import com.project.sooktoring.mentoring.repository.custom.MentoringRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MentoringRepository extends JpaRepository<Mentoring, Long>, MentoringRepositoryCustom {

    @Modifying
    @Query("update Mentoring mtr set mtr.mentorProfile.id = null where mtr.mentorProfile.id = :profileId")
    void updateMentorByProfileId(Long profileId);

    @Modifying
    @Query("update Mentoring mtr set mtr.menteeProfile.id = null where mtr.menteeProfile.id = :profileId")
    void updateMenteeByProfileId(Long profileId);

    @Query("select mtr from Mentoring mtr where mtr.mentorProfile.id = :mentorProfileId and mtr.cat = :cat")
    List<Mentoring> findByMentorProfileIdAndCat(Long mentorProfileId, MentoringCat cat);

    @Query("select mtr from Mentoring mtr where mtr.mentorProfile.id = :mentorProfileId and mtr.state = :state")
    List<Mentoring> findByMentorProfileIdAndState(Long mentorProfileId, MentoringState state);

    @Query("select mtr from Mentoring mtr where mtr.menteeProfile.id = :menteeProfileId and mtr.state = :state")
    List<Mentoring> findByMenteeProfileIdAndState(Long menteeProfileId, MentoringState state);

    @Query("select mtr from Mentoring mtr where mtr.mentorProfile.id = :mentorProfileId")
    List<Mentoring> findByMentorProfileId(Long mentorProfileId);

    @Query("select mtr from Mentoring mtr where mtr.menteeProfile.id = :menteeProfileId")
    List<Mentoring> findByMenteeProfileId(Long menteeProfileId);
}