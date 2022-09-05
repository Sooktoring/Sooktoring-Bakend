package com.project.sooktoring.contest.service;

import com.project.sooktoring.contest.domain.Contest;
import com.project.sooktoring.contest.dto.response.ContestListResponse;
import com.project.sooktoring.contest.dto.response.ContestRoleResponse;
import com.project.sooktoring.contest.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContestService {

    private final ContestRepository contestRepository;

    public List<ContestListResponse> getContestList() {
        List<ContestListResponse> contestListResponses = new ArrayList<>();

        List<Contest> contests = contestRepository.findAll();
        for (Contest contest : contests) {
            ContestListResponse contestListResponse = ContestListResponse.create(contest);
            List<ContestRoleResponse> contestRoles = contest.getContestRoles().stream().map(ContestRoleResponse::create).collect(Collectors.toList());
            contestListResponse.changeContestRoles(contestRoles);
            contestListResponses.add(contestListResponse);
        }

        return contestListResponses;
    }
}
