package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.dto.response.MentoringToListResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringToResponse;
import com.project.sooktoring.mentoring.service.MentoringService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "멘토링 신청 API - 멘토")
@RequiredArgsConstructor
@RequestMapping("/mentoring/to")
@RestController
public class MentoringToController {

    private final MentoringService mentoringService;

    //현재 이용자가 멘토인 경우에 한해서만
    @Operation(summary = "나에게 요청된 멘토링 신청내역 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 리스트 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필")
    })
    @GetMapping
    public List<MentoringToListResponse> getMentoringListToMe() {
        return mentoringService.getMentoringListToMe();
    }


    @Operation(summary = "나에게 요청된 멘토링 신청내역 상세 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 상세 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링")
    })
    @GetMapping("/{mentoringId}")
    public MentoringToResponse getMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        return mentoringService.getMentoringToMe(mentoringId);
    }


    @Operation(summary = "나에게 요청된 멘토링 신청내역 수락", description = "멘토링 신청 상태일 때만 가능", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 수락된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근 or 수락 권한 없는 멘토링 수락"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링")
    })
    @PostMapping("/{mentoringId}")
    public ResponseEntity<Void> acceptMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.accept(mentoringId);
        return ResponseEntity.status(OK).build();
    }


    @Operation(summary = "나에게 요청된 멘토링 신청내역 거절", description = "멘토링 신청 상태일 때만 가능", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 거절된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근 or 거절 권한 없는 멘토링 거절"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링")
    })
    @PutMapping("/{mentoringId}")
    public ResponseEntity<Void> rejectMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.reject(mentoringId);
        return ResponseEntity.status(OK).build();
    }


    @Operation(summary = "진행 중인 멘토링 종료 요청", description = "멘토링 승인 상태 or 멘티가 멘토링 종료 요청한 상태일 때만 가능", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 종료된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근 or 종료 권한 없는 멘토링 종료"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링"),
            @ApiResponse(responseCode = "500", description = "멘토링 푸시 알림 실패")
    })
    @DeleteMapping("/{mentoringId}")
    public ResponseEntity<Void> endMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.endByMentor(mentoringId);
        return ResponseEntity.status(OK).build();
    }
}
