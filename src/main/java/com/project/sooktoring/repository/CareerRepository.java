package com.project.sooktoring.repository;

import com.project.sooktoring.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

//    @Query("select c from Career c where c.userProfile.id = :userId")
//    List<Career> findByUserId(Long userId);
}
