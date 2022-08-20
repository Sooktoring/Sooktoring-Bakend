package com.project.sooktoring.profile.repository;

import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.profile.repository.custom.ProfileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileRepositoryCustom {
}
