package com.project.sooktoring.club.repository;

import com.project.sooktoring.club.domain.ClubUrl;
import com.project.sooktoring.club.dto.response.ClubUrlResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubUrlRepository extends JpaRepository<ClubUrl, Long> {

    @Query("select new com.project.sooktoring.club.dto.response.ClubUrlResponse(cu.id, cu.cat, cu.url)" +
            " from ClubUrl cu where cu.club.id = :clubId")
    List<ClubUrlResponse> findAllByClubId(@Param("clubId") Long clubId);

    @Modifying
    @Query("delete from ClubUrl cu where cu.club.id = :clubId")
    void deleteByClubId(@Param("clubId") Long clubId);

    @Query("select cu.url from ClubUrl cu where cu.club.id = :clubId and cu.cat = 'RECRUIT' order by cu.createdDate asc, cu.id asc")
    List<String> findAllRecruitUrlByClubId(@Param("clubId") Long clubId);

    @Modifying
    @Query("delete from ClubUrl cu where cu.club.id = :clubId and cu.id not in :clubUrlIds")
    void deleteByIdsNotInBatch(@Param("clubId") Long clubId, @Param("clubUrlIds") List<Long> clubUrlIds);
}
