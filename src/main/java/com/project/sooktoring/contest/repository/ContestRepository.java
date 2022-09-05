package com.project.sooktoring.contest.repository;

import com.project.sooktoring.contest.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}
