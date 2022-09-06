package com.project.sooktoring.contest.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.contest.domain.Contest;
import com.project.sooktoring.contest.domain.ContestRole;
import com.project.sooktoring.contest.dto.request.ContestRequest;
import com.project.sooktoring.contest.dto.request.ContestRoleRequest;
import com.project.sooktoring.contest.dto.response.ContestListResponse;
import com.project.sooktoring.contest.dto.response.ContestRoleResponse;
import com.project.sooktoring.contest.repository.ContestRepository;
import com.project.sooktoring.contest.repository.ContestRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.sooktoring.common.exception.ErrorCode.NOT_FOUND_CONTEST;

@RequiredArgsConstructor
@Service
public class ContestService {

    private final ContestRepository contestRepository;
    private final ContestRoleRepository contestRoleRepository;

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

    @Transactional
    public void saveContest(ContestRequest contestRequest) {
        Contest contest = Contest.builder()
                .name(contestRequest.getContestName())
                .deadline(contestRequest.getDeadline())
                .build();
        contestRepository.save(contest);

        List<ContestRoleRequest> contestRoleRequests = contestRequest.getContestRoles();
        for (ContestRoleRequest contestRoleRequest : contestRoleRequests) {
            ContestRole contestRole = ContestRole.builder()
                    .contest(contest)
                    .name(contestRoleRequest.getContestRoleName())
                    .build();
            contestRoleRepository.save(contestRole);
        }
    }

    @Transactional
    public void updateContest(Long contestId, ContestRequest contestRequest) {
        Contest contest = _getContest(contestId);
        contest.update(contestRequest.getContestName(), contestRequest.getDeadline());

        _updateAndDeleteContestRole(contestRequest, contest);
        _saveContestRole(contestRequest, contest);
    }

    @Transactional
    public void deleteContest(Long contestId) {
        Contest contest = _getContest(contestId);
        contestRoleRepository.deleteByContestId(contestId);
        contestRepository.delete(contest);
    }

    //=== private 메소드 ===

    private Contest _getContest(Long contestId) {
        return contestRepository.findById(contestId).orElseThrow(() -> new CustomException(NOT_FOUND_CONTEST));
    }

    private void _updateAndDeleteContestRole(ContestRequest contestRequest, Contest contest) {
        Map<Long, ContestRole> contestRoleMap = contest.getContestRoles().stream().collect(Collectors.toMap(ContestRole::getId, contestRole -> contestRole));
        Map<Long, ContestRoleRequest> contestRoleRequestMap = contestRequest.getContestRoles().stream()
                .filter(contestRoleRequest -> contestRoleRequest.getContestRoleId() != null)
                .collect(Collectors.toMap(ContestRoleRequest::getContestRoleId, contestRoleRequest -> contestRoleRequest));
        List<ContestRole> deleteList = new ArrayList<>();

        for (Long contestRoleId : contestRoleMap.keySet()) {
            ContestRoleRequest contestRoleRequest = contestRoleRequestMap.get(contestRoleId);
            if (contestRoleRequest == null) {
                deleteList.add(contestRoleMap.get(contestRoleId)); //삭제
            }
            else {
                contestRoleMap.get(contestRoleId).update(contestRoleRequest.getContestRoleName(), contestRoleRequest.getIsRecruit()); //수정
            }
        }
        contestRoleRepository.deleteAll(deleteList);
    }

    private void _saveContestRole(ContestRequest contestRequest, Contest contest) {
        List<ContestRole> contestRoles = contestRequest.getContestRoles().stream()
                .filter(contestRoleRequest -> contestRoleRequest.getContestRoleId() == null)
                .map(contestRoleRequest -> ContestRole.builder()
                        .contest(contest)
                        .name(contestRoleRequest.getContestRoleName())
                        .build()
                ).collect(Collectors.toList());
        contestRoleRepository.saveAll(contestRoles);
    }
}
