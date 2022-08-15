package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.dto.response.MtrFromListResponse;
import com.project.sooktoring.mentoring.dto.response.MtrFromResponse;
import com.project.sooktoring.mentoring.dto.response.MtrToListResponse;
import com.project.sooktoring.mentoring.dto.response.MtrToResponse;
import com.project.sooktoring.user.auth.util.CurrentUser;
import com.project.sooktoring.user.auth.util.UserPrincipal;
import com.project.sooktoring.mentoring.dto.request.MtrRequest;
import com.project.sooktoring.mentoring.dto.request.MtrUpdateRequest;
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
    public List<MtrFromListResponse> getMyMentoringList(@CurrentUser UserPrincipal currentUser) {
        return mentoringService.getMyMentoringList(currentUser.getUserId());
    }

    @Operation(summary = "나의 멘토링 신청내역 상세 조회")
    @GetMapping("/from/{mtrId}")
    public MtrFromResponse getMyMentoring(@CurrentUser UserPrincipal currentUser,
                                          @Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        return mentoringService.getMyMentoring(currentUser.getUserId(), mtrId);
    }

    @Operation(summary = "멘토링 신청내역 등록", description = "상태 기본값 APPLY")
    @PostMapping("/from")
    public String saveMentoring(@CurrentUser UserPrincipal currentUser,
                                @RequestBody MtrRequest mtrRequest) {
        mentoringService.save(currentUser.getUserId(), mtrRequest);
        return "멘토링 신청이 완료되었습니다.";
    }

    @Operation(summary = "멘토링 신청내역 수정", description = "APPLY, INVALID 상태일 때만 가능")
    @PutMapping("/from/{mtrId}")
    public String updateMentoring(@CurrentUser UserPrincipal currentUser,
                                  @Parameter(description = "멘토링 id") @PathVariable Long mtrId,
                                  @RequestBody MtrUpdateRequest mtrUpdateRequest) {
        //카테고리, 이유, 한마디
        mentoringService.update(currentUser.getUserId(), mtrId, mtrUpdateRequest);
        return "멘토링 수정이 완료되었습니다.";
    }

    @Operation(summary = "멘토링 신청내역 삭제", description = "APPLY, REJECT, INVALID, WITHDRAW 상태일 때만 가능")
    @DeleteMapping("/from/{mtrId}")
    public String cancelMentoring(@CurrentUser UserPrincipal currentUser,
                                  @Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        mentoringService.cancel(currentUser.getUserId(), mtrId);
        return "멘토링 취소가 완료되었습니다";
    }

    //현재 이용자가 멘토인 경우에 한해서만
    @Operation(summary = "나에게 요청된 멘토링 신청내역 리스트 조회")
    @GetMapping("/to")
    public List<MtrToListResponse> getMentoringListToMe(@CurrentUser UserPrincipal currentUser) {
        return mentoringService.getMentoringListToMe(currentUser.getUserId());
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 상세 조회")
    @GetMapping("/to/{mtrId}")
    public MtrToResponse getMentoringToMe(@CurrentUser UserPrincipal currentUser,
                                          @Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        return mentoringService.getMentoringToMe(currentUser.getUserId(), mtrId);
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 수락", description = "ACCEPT 상태로 변경, 이후 END 로만 상태변경 가능")
    @PostMapping("/to/{mtrId}")
    public String acceptMentoringToMe(@CurrentUser UserPrincipal currentUser,
                                      @Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        mentoringService.accept(currentUser.getUserId(), mtrId);
        return "멘토링 신청을 수락하였습니다";
    }

    @Operation(summary = "나에게 요청된 멘토링 신청내역 거부", description = "REJECT 상태로 변경, 번복 불가")
    @PutMapping("/to/{mtrId}")
    public String rejectMentoringToMe(@CurrentUser UserPrincipal currentUser,
                                      @Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        mentoringService.reject(currentUser.getUserId(), mtrId);
        return "멘토링 신청을 거부하였습니다";
    }

    @Operation(summary = "수락한 멘토링 진행 후 종료", description = "END 상태로 변경, ACCEPT 상태일 때만 가능")
    @DeleteMapping("/to/{mtrId}")
    public String endMentoringToMe(@CurrentUser UserPrincipal currentUser,
                                   @Parameter(description = "멘토링 id") @PathVariable Long mtrId) {
        mentoringService.end(currentUser.getUserId(), mtrId);
        return "멘토링 진행을 종료합니다.";
    }

    @Operation(summary = "채팅방 목록 조회")
    @PostMapping("/chat/list")
    public List<Mentoring> getMyChatRoomList(@CurrentUser UserPrincipal currentUser) {
        return mentoringService.getMyChatRoomList(currentUser.getUserId());
    }
}
