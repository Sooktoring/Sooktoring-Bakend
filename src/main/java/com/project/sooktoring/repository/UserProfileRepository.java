package com.project.sooktoring.repository;

import com.project.sooktoring.domain.UserProfile;
import com.project.sooktoring.repository.custom.UserProfileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileRepositoryCustom {
}
