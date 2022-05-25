package com.project.sooktoring.repository;

import com.project.sooktoring.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileRepositoryCustom {

}
