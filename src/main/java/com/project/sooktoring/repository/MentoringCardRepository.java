package com.project.sooktoring.repository;

import com.project.sooktoring.domain.MentoringCard;
import com.project.sooktoring.repository.custom.MentoringCardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringCardRepository extends JpaRepository<MentoringCard, Long>, MentoringCardRepositoryCustom {
}
