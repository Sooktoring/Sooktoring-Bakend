package com.project.sooktoring.repository;

import com.project.sooktoring.domain.Career;
import com.project.sooktoring.repository.custom.CareerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long>, CareerRepositoryCustom {

    @Modifying
    @Query("delete from Career c where c.userProfile.id = :userId")
    void deleteByUserId(Long userId);
}
