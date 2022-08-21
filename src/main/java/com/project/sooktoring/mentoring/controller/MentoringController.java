package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.dto.response.MentoringFromListResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringFromResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringToListResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringToResponse;
import com.project.sooktoring.mentoring.dto.request.MentoringRequest;
import com.project.sooktoring.mentoring.dto.request.MentoringUpdateRequest;
import com.project.sooktoring.mentoring.service.MentoringService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentoring")
@RequiredArgsConstructor
@Api(tags = "멘토링 API")
public class MentoringController {

    private final MentoringService mentoringService;

    @Operation(summary = "나의 멘토링 신청내역 리스트 조회")
    @GetMapping("/from")
    public List<MentoringFromListResponse> getMyMentoringList() {
        return mentoringService.getMyMentoringList();
    }

    @Operation(summary = "나의 멘토링 신청내역 상세 조회")
    @GetMapping("/from/{mentoringId}")
    public MentoringFromResponse getMyMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        return mentoringService.getMyMentoring(mentoringId);
    }

    @Operation(summary = "멘토링 신청내역 등록", description = "상태 기본값 APPLY")
    @PostMapping("/from")
    public String saveMentoring(@RequestBody MentoringRequest mtrRequest) {
        mentoringService.save(mtrRequest);
        return "멘토링 신청이 완료되었습니다.";
    }

    @Operation(summary = "멘토링 신청내역 수정", description = "APPLY, INVALID 상태일 때만 가능")
    @PutMapping("/from/{mentoringId}")
    public String updateMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId,
                                  @RequestBody MentoringUpdateRequest mtrUpdateRequest) {
        //카테고리, 이유, 한마디
        mentoringService.update(mentoringId, mtrUpdateRequest);
        return "멘토링 수정이 완료되었습니다.";
    }

    @Operation(summary = "멘토링 신청내역 삭제", description = "APPLY, REJECT, INVALID, WITHDRAW 상태일 때만 가능")
    @DeleteMapping("/from/{mentoringId}")
    public String cancelMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.cancel(mentoringId);
        return "멘토링 취소가 완료되었습니다";
    }

    //현재 이용자가 멘토인 경우에 한해서만
    @Operation(summary = "나에게 요청된 멘토링 신청내역 리스트 조회")
    @GetMapping("/to")
    public List<MentoringToListResponse> getMentoringListToMe() {
        return mentoringService.getMentoringListToMe();
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 상세 조회")
    @GetMapping("/to/{mentoringId}")
    public MentoringToResponse getMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        return mentoringService.getMentoringToMe(mentoringId);
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 수락", description = "ACCEPT 상태로 변경, 이후 END 로만 상태변경 가능")
    @PostMapping("/to/{mentoringId}")
    public String acceptMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.accept(mentoringId);
        return "멘토링 신청을 수락하였습니다";
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 거부", description = "REJECT 상태로 변경, 번복 불가")
    @PutMapping("/to/{mentoringId}")
    public String rejectMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.reject(mentoringId);
        return "멘토링 신청을 거부하였습니다";
    }

    @Operation(summary = "수락한 멘토링 진행 후 종료", description = "END 상태로 변경, ACCEPT 상태일 때만 가능")
    @DeleteMapping("/to/{mentoringId}")
    public String endMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId) {
        mentoringService.end(mentoringId);
        return "멘토링 진행을 종료합니다.";
    }

    @Operation(summary = "채팅방 목록 조회")
    @PostMapping("/chat/list")
    public List<Mentoring> getMyChatRoomList() {
        return mentoringService.getMyChatRoomList();
    }
}
