package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.dto.request.MentoringRequest;
import com.project.sooktoring.mentoring.dto.request.MentoringUpdateRequest;
import com.project.sooktoring.mentoring.dto.response.MentoringFromListResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringFromResponse;
import com.project.sooktoring.mentoring.service.MentoringService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "나의 멘토링 신청내역 리스트 조회")
    @GetMapping
    public List<MentoringFromListResponse> getMyMentoringList() {
        return mentoringService.getMyMentoringList();
    }

    @Operation(summary = "나의 멘토링 신청내역 상세 조회")
    @GetMapping("/{mentoringId}")
    public MentoringFromResponse getMyMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        return mentoringService.getMyMentoring(mentoringId);
    }

    //일단 DTO 반환 안하고 상태코드만 반환
    @Operation(summary = "멘토링 신청내역 등록", description = "상태 기본값 APPLY")
    @PostMapping
    public ResponseEntity<Void> saveMentoring(@RequestBody MentoringRequest mentoringRequest) {
        mentoringService.save(mentoringRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @Operation(summary = "멘토링 신청내역 수정", description = "APPLY, INVALID 상태일 때만 가능")
    @PutMapping("/{mentoringId}")
    public ResponseEntity<Void> updateMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId,
                                                @RequestBody MentoringUpdateRequest mentoringUpdateRequest) {
        mentoringService.update(mentoringId, mentoringUpdateRequest);
        return ResponseEntity.status(OK).build();
    }

    @Operation(summary = "멘토링 신청내역 삭제", description = "APPLY, REJECT, INVALID, WITHDRAW 상태일 때만 가능")
    @DeleteMapping("/{mentoringId}")
    public ResponseEntity<Void> cancelMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.cancel(mentoringId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
