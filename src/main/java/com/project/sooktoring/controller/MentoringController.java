package com.project.sooktoring.controller;

import com.project.sooktoring.auth.user.CurrentUser;
import com.project.sooktoring.auth.user.UserPrincipal;
import com.project.sooktoring.dto.request.MtrRequest;
import com.project.sooktoring.dto.request.MtrUpdateRequest;
import com.project.sooktoring.dto.response.MentorResponse;
import com.project.sooktoring.dto.response.MtrFromResponse;
import com.project.sooktoring.dto.response.MtrToResponse;
import com.project.sooktoring.service.MentoringService;
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

    @Operation(summary = "멘토들의 프로필 조회", description = "멘토 아이디, 이름, 직업, 연차, 주전공")
    @GetMapping("/mentors")
    public List<MentorResponse> getMentorList() {
        return mentoringService.getMentorList();
    }

    @Operation(summary = "특정 멘토의 프로필 조회", description = "멘토 아이디, 이름, 직업, 연차, 주전공")
    @GetMapping("/mentors/{mentorId}")
    public MentorResponse getMentor(@PathVariable Long mentorId) {
        return mentoringService.getMentor(mentorId);
    }

    @Operation(summary = "나의 멘토링 신청내역 리스트 조회", description = "나의 멘토링 신청내역 리스트 조회 - 멘토 정보 포함")
    @GetMapping("/from")
    public List<MtrFromResponse> getMyMentoringList(@CurrentUser UserPrincipal currentUser) {
        //멘토, 카테고리, 이유, 한마디, 수락 여부
        return mentoringService.getMyMentoringList(currentUser.getUserId());
    }

    @Operation(summary = "나의 멘토링 신청내역 상세조회", description = "나의 멘토링 신청내역 상세조회 - 멘토 정보 포함")
    @GetMapping("/from/{mtrId}")
    public MtrFromResponse getMyMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        //멘토, 카테고리, 이유, 한마디, 수락 여부
        return mentoringService.getMyMentoring(mtrId);
    }

    @Operation(summary = "멘토링 신청내역 등록", description = "멘토링 신청내역 등록 - 승인여부 기본값 false")
    @PostMapping("/from")
    public String saveMentoring(@RequestBody MtrRequest mtrRequest,
                                @CurrentUser UserPrincipal currentUser) {
        mentoringService.save(mtrRequest, currentUser.getUserId());
        return "멘토링 신청이 완료되었습니다.";
    }

    @Operation(summary = "멘토링 신청내역 수정", description = "멘토링 신청내역 수정 - 승인여부 값 수정 불가")
    @PutMapping("/from/{mtrId}")
    public String updateMentoring(@RequestBody MtrUpdateRequest mtrUpdateRequest,
                                  @Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        //카테고리, 이유, 한마디
        mentoringService.update(mtrUpdateRequest, mtrId);
        return "멘토링 수정이 완료되었습니다.";
    }

    @Operation(summary = "멘토링 신청내역 삭제", description = "멘토링 신청내역 삭제 - 멘토가 수락한 경우 삭제 불가")
    @DeleteMapping("/from/{mtrId}")
    public String cancelMentoring(@Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        return mentoringService.cancel(mtrId) ? "멘토링 취소가 완료되었습니다" : "해당 멘토링 신청이 존재하지 않거나 멘토가 수락한 멘토링입니다.";
    }

    //현재 이용자가 멘토인 경우에 한해서만
    @Operation(summary = "나에게 요청된 멘토링 신청내역 리스트 조회", description = "현재 이용자가 멘토인 경우 나에게 요청된 멘토링 신청내역 리스트 조회 - 멘티 정보 포함")
    @GetMapping("/to")
    public List<MtrToResponse> getMentoringListToMe(@CurrentUser UserPrincipal currentUser) {
        //멘티, 카테고리, 이유, 한마디, 수락 여부
        return mentoringService.getMentoringListToMe(currentUser.getUserId());
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 상세조회", description = "현재 이용자가 멘토인 경우 나에게 요청된 멘토링 신청내역 상세조회 - 멘티 정보 포함")
    @GetMapping("/to/{mtrId}")
    public MtrToResponse getMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        //멘티, 카테고리, 이유, 한마디, 수락 여부
        return mentoringService.getMentoringToMe(mtrId);
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 수락", description = "나에게 요청된 멘토링 신청내역 수락 - 채팅방 자동 생성")
    @PutMapping("/to/{mtrId}")
    public String acceptMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        mentoringService.accept(mtrId);
        return "멘토링 신청을 수락하였습니다";
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 거부", description = "나에게 요청된 멘토링 신청내역 거부 - 채팅방이 존재하는 경우 채팅방 자동 소멸")
    @DeleteMapping("/to/{mtrId}")
    public String rejectMentoringToMe(@Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        mentoringService.reject(mtrId);
        return "멘토링 신청을 거부하였습니다";
    }
}
