package com.project.sooktoring.profile.repository;

import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.profile.repository.custom.ProfileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileRepositoryCustom {

    @Query("select p from Profile p where p.user.id = :userId")
    Optional<Profile> findByUserId(@Param("userId") Long userId);
}
