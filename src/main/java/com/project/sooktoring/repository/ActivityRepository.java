package com.project.sooktoring.repository;

import com.project.sooktoring.domain.Activity;
import com.project.sooktoring.repository.custom.ActivityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryCustom {

    @Modifying
    @Query("delete from Activity a where a.userProfile.id = :userId")
    void deleteByUserId(Long userId);
}
