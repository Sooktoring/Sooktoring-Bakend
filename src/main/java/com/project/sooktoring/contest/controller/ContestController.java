package com.project.sooktoring.contest.controller;

import com.project.sooktoring.contest.dto.request.ContestRequest;
import com.project.sooktoring.contest.dto.response.ContestListResponse;
import com.project.sooktoring.contest.service.ContestService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "공모전 API")
@RequiredArgsConstructor
@RequestMapping("/contests")
@RestController
public class ContestController {

    private final ContestService contestService;

    @GetMapping
    public List<ContestListResponse> getContestList() {
        return contestService.getContestList();
    }

    @PostMapping
    public ResponseEntity<Void> saveContest(@RequestBody ContestRequest contestRequest) {
        contestService.saveContest(contestRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{contestId}")
    public ResponseEntity<Void> updateContest(@PathVariable Long contestId,
                                              @RequestBody ContestRequest contestRequest) {
        contestService.updateContest(contestId, contestRequest);
        return ResponseEntity.status(OK). build();
    }

    @DeleteMapping("/{contestId}")
    public ResponseEntity<Void> deleteContest(@PathVariable Long contestId) {
        contestService.deleteContest(contestId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
