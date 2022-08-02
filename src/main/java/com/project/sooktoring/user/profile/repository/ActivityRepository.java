package com.project.sooktoring.user.profile.repository;

import com.project.sooktoring.user.profile.domain.Activity;
import com.project.sooktoring.user.profile.repository.custom.ActivityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryCustom {

    @Modifying
    @Query("delete from Activity a where a.userProfile.id = :userId")
    void deleteByUserId(Long userId);
}
