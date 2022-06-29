package com.project.sooktoring.repository;

import com.project.sooktoring.domain.Activity;
import com.project.sooktoring.repository.custom.ActivityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryCustom {

//    @Query("select a from Activity a where a.userProfile.id = :userId")
//    List<Activity> findByUserId(Long userId);
}
