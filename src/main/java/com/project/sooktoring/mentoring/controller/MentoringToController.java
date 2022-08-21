package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.dto.response.MentoringToListResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringToResponse;
import com.project.sooktoring.mentoring.service.MentoringService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "나에게 요청된 멘토링 신청내역 리스트 조회")
    @GetMapping
    public List<MentoringToListResponse> getMentoringListToMe() {
        return mentoringService.getMentoringListToMe();
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 상세 조회")
    @GetMapping("/{mentoringId}")
    public MentoringToResponse getMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        return mentoringService.getMentoringToMe(mentoringId);
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 수락", description = "ACCEPT 상태로 변경, 이후 END 로만 상태변경 가능")
    @PostMapping("/{mentoringId}")
    public ResponseEntity<Void> acceptMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.accept(mentoringId);
        return ResponseEntity.status(OK).build();
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 거부", description = "REJECT 상태로 변경, 번복 불가")
    @PutMapping("/{mentoringId}")
    public ResponseEntity<Void> rejectMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.reject(mentoringId);
        return ResponseEntity.status(OK).build();
    }

    @Operation(summary = "수락한 멘토링 진행 후 종료", description = "END 상태로 변경, ACCEPT 상태일 때만 가능")
    @DeleteMapping("/{mentoringId}")
    public ResponseEntity<Void> endMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.end(mentoringId);
        return ResponseEntity.status(OK).build();
    }
}
