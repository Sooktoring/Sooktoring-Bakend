package com.project.sooktoring.controller;

import com.project.sooktoring.auth.user.CurrentUser;
import com.project.sooktoring.auth.user.UserPrincipal;
import com.project.sooktoring.domain.Mentoring;
import com.project.sooktoring.dto.request.MtrRequest;
import com.project.sooktoring.dto.request.MtrUpdateRequest;
import com.project.sooktoring.dto.response.MtrFromResponse;
import com.project.sooktoring.dto.response.MtrToResponse;
import com.project.sooktoring.service.MentoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentoring")
@RequiredArgsConstructor
public class MentoringController {

    private final MentoringService mentoringService;

    @GetMapping("/from")
    public List<MtrFromResponse> getMyMentoringList(@CurrentUser UserPrincipal currentUser) {
        //멘토, 카테고리, 이유, 한마디, 수락 여부
        return mentoringService.getMyMentoringList(currentUser.getUserId());
    }

    @GetMapping("/from/{mtrId}")
    public MtrFromResponse getMyMentoring(@PathVariable Long mtrId) {
        //멘토, 카테고리, 이유, 한마디, 수락 여부
        return mentoringService.getMyMentoring(mtrId);
    }

    @PostMapping("/from")
    public String saveMentoring(@RequestBody MtrRequest mtrRequest,
                                @CurrentUser UserPrincipal currentUser) {
        mentoringService.save(mtrRequest, currentUser.getUserId());
        return "멘토링 신청이 완료되었습니다.";
    }

    @PutMapping("/from/{mtrId}")
    public String updateMentoring(@RequestBody MtrUpdateRequest mtrUpdateRequest,
                                  @PathVariable Long mtrId) {
        //카테고리, 이유, 한마디
        mentoringService.update(mtrUpdateRequest, mtrId);
        return "멘토링 수정이 완료되었습니다.";
    }

    @DeleteMapping("/from/{mtrId}")
    public String cancelMentoring(@PathVariable Long mtrId) {
        return mentoringService.cancel(mtrId) ? "멘토링 취소가 완료되었습니다" : "해당 멘토링 신청이 존재하지 않거나 멘토가 수락한 멘토링입니다.";
    }

    //현재 이용자가 멘토인 경우에 한해서만
    @GetMapping("/to")
    public List<MtrToResponse> getMentoringListToMe(@CurrentUser UserPrincipal currentUser) {
        //멘티, 카테고리, 이유, 한마디, 수락 여부
        return mentoringService.getMentoringListToMe(currentUser.getUserId());
    }

    @GetMapping("/to/{mtrId}")
    public MtrToResponse getMentoringToMe(@PathVariable Long mtrId) {
        //멘티, 카테고리, 이유, 한마디, 수락 여부
        return mentoringService.getMentoringToMe(mtrId);
    }

    @PutMapping("/to/{mtrId}")
    public String acceptMentoringToMe(@PathVariable Long mtrId) {
        mentoringService.accept(mtrId);
        return "멘토링 신청을 수락하였습니다";
    }

    @DeleteMapping("/to/{mtrId}")
    public String rejectMentoringToMe(@PathVariable Long mtrId) {
        mentoringService.reject(mtrId);
        return "멘토링 신청을 거부하였습니다";
    }
}
