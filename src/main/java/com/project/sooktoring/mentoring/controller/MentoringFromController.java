package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.dto.request.MentoringRequest;
import com.project.sooktoring.mentoring.dto.request.MentoringUpdateRequest;
import com.project.sooktoring.mentoring.dto.response.MentoringFromListResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringFromResponse;
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

@Api(tags = "멘토링 신청 API - 멘티")
@RequiredArgsConstructor
@RequestMapping("/mentoring/from")
@RestController
public class MentoringFromController {

    private final MentoringService mentoringService;

    @Operation(summary = "나의 멘토링 신청내역 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 리스트 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필")
    })
    @GetMapping
    public List<MentoringFromListResponse> getMyMentoringList() {
        return mentoringService.getMyMentoringList();
    }


    @Operation(summary = "나의 멘토링 신청내역 상세 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 상세 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링")
    })
    @GetMapping("/{mentoringId}")
    public MentoringFromResponse getMyMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        return mentoringService.getMyMentoring(mentoringId);
    }


    //일단 DTO 반환 안하고 상태코드만 반환
    @Operation(summary = "멘토링 신청내역 등록", description = "상태 기본값 APPLY", responses = {
            @ApiResponse(responseCode = "201", description = "정상적으로 멘토링 신청내역 등록된 경우"),
            @ApiResponse(responseCode = "400", description = "같은 멘토링 신청내역 존재 or 멘토 == 멘티 or 멘티에게 멘토링 신청"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 멘토 or 멘티 프로필")
    })
    @PostMapping
    public ResponseEntity<Void> saveMentoring(@RequestBody MentoringRequest mentoringRequest) {
        mentoringService.save(mentoringRequest);
        return ResponseEntity.status(CREATED).build();
    }


    @Operation(summary = "멘토링 신청내역 수정", description = "멘토링 신청 상태일 때만 가능", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 수정된 경우"),
            @ApiResponse(responseCode = "400", description = "같은 멘토링 신청내역 존재"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근 or 수정 권한 없는 멘토링 수정"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링")
    })
    @PutMapping("/{mentoringId}")
    public ResponseEntity<Void> updateMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId,
                                                @RequestBody MentoringUpdateRequest mentoringUpdateRequest) {
        mentoringService.update(mentoringId, mentoringUpdateRequest);
        return ResponseEntity.status(OK).build();
    }


    @Operation(summary = "멘토링 신청내역 삭제", description = "신청, 거절, 탈퇴 상태일 때만 가능", responses = {
            @ApiResponse(responseCode = "204", description = "정상적으로 멘토링 신청내역 삭제된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근 or 삭제 권한 없는 멘토링 삭제"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링")
    })
    @DeleteMapping("/{mentoringId}")
    public ResponseEntity<Void> cancelMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.cancel(mentoringId);
        return ResponseEntity.status(NO_CONTENT).build();
    }


    @Operation(summary = "진행 중인 멘토링 종료 요청", description = "멘토링 승인 상태 or 멘토가 멘토링 종료 요청한 상태일 때만 가능", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 멘토링 신청내역 종료된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근 or 종료 권한 없는 멘토링 종료"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링"),
            @ApiResponse(responseCode = "500", description = "멘토링 푸시 알림 실패")
    })
    @PutMapping("/{mentoringId}/end")
    public ResponseEntity<Void> endMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.endByMentee(mentoringId);
        return ResponseEntity.status(OK).build();
    }
}
