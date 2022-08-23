package com.project.sooktoring.club.repository.custom;

import com.project.sooktoring.club.dto.response.ClubListResponse;
import com.project.sooktoring.club.dto.response.ClubRecruitListResponse;
import com.project.sooktoring.club.enumerate.ClubKind;

import java.util.List;

public interface ClubRepositoryCustom {

    List<ClubListResponse> findAllByNewOrder(ClubKind kind);
    List<ClubRecruitListResponse> findAllRecruitByNewOrder();
}
