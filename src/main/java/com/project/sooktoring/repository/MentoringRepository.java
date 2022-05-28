package com.project.sooktoring.repository;

import com.project.sooktoring.domain.Mentoring;
import com.project.sooktoring.repository.custom.MentoringRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentoringRepository extends JpaRepository<Mentoring, Long>, MentoringRepositoryCustom {


}
