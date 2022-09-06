package com.project.sooktoring.contest.repository;

import com.project.sooktoring.contest.domain.ContestRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContestRoleRepository extends JpaRepository<ContestRole, Long> {

    @Modifying
    @Query("delete from ContestRole cr where cr.contest.id = :contestId")
    void deleteByContestId(@Param("contestId") Long contestId);
}
