package com.project.sooktoring.mentoring.repository;

import com.project.sooktoring.mentoring.domain.MentoringCard;
import com.project.sooktoring.mentoring.repository.custom.MentoringCardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringCardRepository extends JpaRepository<MentoringCard, Long>, MentoringCardRepositoryCustom {
}
