package com.project.sooktoring.profile.repository;

import com.project.sooktoring.profile.domain.Career;
import com.project.sooktoring.profile.repository.custom.CareerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CareerRepository extends JpaRepository<Career, Long>, CareerRepositoryCustom {

    @Modifying
    @Query("delete from Career c where c.profile.id = :userId")
    void deleteByUserId(Long userId);
}
