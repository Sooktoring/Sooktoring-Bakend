package com.project.sooktoring.contest.controller;

import com.project.sooktoring.contest.dto.request.ContestRequest;
import com.project.sooktoring.contest.dto.response.ContestListResponse;
import com.project.sooktoring.contest.service.ContestService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "공모전 모집 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 공모전 리스트 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰")
    })
    @GetMapping
    public List<ContestListResponse> getContestList() {
        return contestService.getContestList();
    }


    @Operation(summary = "공모전 모집 등록", responses = {
            @ApiResponse(responseCode = "201", description = "정상적으로 공모전 모집 등록된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰")
    })
    @PostMapping
    public ResponseEntity<Void> saveContest(@RequestBody ContestRequest contestRequest) {
        contestService.saveContest(contestRequest);
        return ResponseEntity.status(CREATED).build();
    }

    
    @Operation(summary = "공모전 모집 수정", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 공모전 모집 수정된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 공모전 모집")
    })
    @PutMapping("/{contestId}")
    public ResponseEntity<Void> updateContest(@Parameter(description = "공모전 id") @PathVariable Long contestId,
                                              @RequestBody ContestRequest contestRequest) {
        contestService.updateContest(contestId, contestRequest);
        return ResponseEntity.status(OK). build();
    }


    @Operation(summary = "공모전 모집 삭제", responses = {
            @ApiResponse(responseCode = "204", description = "정상적으로 공모전 모집 삭제된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 공모전 모집")
    })
    @DeleteMapping("/{contestId}")
    public ResponseEntity<Void> deleteContest(@Parameter(description = "공모전 id") @PathVariable Long contestId) {
        contestService.deleteContest(contestId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
