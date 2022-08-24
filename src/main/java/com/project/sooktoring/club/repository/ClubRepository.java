package com.project.sooktoring.club.repository;

import com.project.sooktoring.club.domain.Club;
import com.project.sooktoring.club.repository.custom.ClubRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom {
}
