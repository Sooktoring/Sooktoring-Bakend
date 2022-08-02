package com.project.sooktoring.user.profile.repository;

import com.project.sooktoring.user.profile.domain.UserProfile;
import com.project.sooktoring.user.profile.repository.custom.UserProfileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileRepositoryCustom {
}
