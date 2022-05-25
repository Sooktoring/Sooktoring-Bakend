package com.project.sooktoring.repository;

import com.project.sooktoring.dto.response.UserProfileResponse;

import java.util.List;

public interface UserProfileRepositoryCustom {

    UserProfileResponse findDtoById(Long id);
    List<UserProfileResponse> findAllDto();
}
