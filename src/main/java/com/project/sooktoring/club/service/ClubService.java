package com.project.sooktoring.club.service;

import com.project.sooktoring.club.dto.request.ClubRequest;
import com.project.sooktoring.club.dto.response.ClubListResponse;
import com.project.sooktoring.club.dto.response.ClubRecruitListResponse;
import com.project.sooktoring.club.dto.response.ClubResponse;
import com.project.sooktoring.club.repository.ClubRepository;
import com.project.sooktoring.club.repository.ClubUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubUrlRepository clubUrlRepository;


    public List<ClubListResponse> getClubInListByNewOrder() {

    }

    public List<ClubListResponse> getClubOutListByNewOrder() {

    }

    public List<ClubRecruitListResponse> getClubRecruitListByNewOrder() {

    }

    public ClubResponse getClub(Long clubId) {

    }

    @Transactional
    public void save(ClubRequest clubRequest) {

    }

    @Transactional
    public void update(Long clubId, ClubRequest clubRequest) {

    }

    @Transactional
    public void delete(Long clubId) {

    }
}
