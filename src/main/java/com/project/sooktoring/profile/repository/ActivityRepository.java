package com.project.sooktoring.profile.repository;

import com.project.sooktoring.profile.domain.Activity;
import com.project.sooktoring.profile.repository.custom.ActivityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryCustom {

    @Modifying
    @Query("delete from Activity a where a.profile.id = :profileId")
    void deleteByProfileId(Long profileId);
}
