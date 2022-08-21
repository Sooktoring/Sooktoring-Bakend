package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.dto.request.MentoringCardRequest;
import com.project.sooktoring.mentoring.dto.response.MentoringCardFromResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringCardToResponse;
import com.project.sooktoring.mentoring.service.MentoringCardService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "멘토링 감사카드 API")
@RequiredArgsConstructor
@RequestMapping("/mentoring/card")
@RestController
public class MentoringCardController {

    private final MentoringCardService mentoringCardService;

    @Operation(summary = "내가 쓴 감사카드 리스트 조회")
    @GetMapping("/from")
    public List<MentoringCardFromResponse> getMentoringCardListFromMe() {
        return mentoringCardService.getMentoringCardListFromMe();
    }

    @Operation(summary = "내가 쓴 감사카드 상세 조회")
    @GetMapping("/from/{mentoringCardId}")
    public MentoringCardFromResponse getMentoringCardFromMe(@PathVariable Long mentoringCardId) {
        return mentoringCardService.getMentoringCardFromMe(mentoringCardId);
    }

    @Operation(summary = "감사카드 작성", description = "해당 멘토링 종료 상태일 때에만 작성 가능")
    @PostMapping("/from/{mentoringId}")
    public String saveMtrCard(@PathVariable Long mentoringId,
                              @RequestBody @Validated MentoringCardRequest mtrCardRequest) {
        mentoringCardService.save(mentoringId, mtrCardRequest);
        return "감사카드 작성이 완료되었습니다.";
    }

    @Operation(summary = "감사카드 수정")
    @PutMapping("/from/{mentoringCardId}")
    public String updateMtrCard(@PathVariable Long mentoringCardId,
                                @RequestBody @Validated MentoringCardRequest mtrCardRequest) {
        mentoringCardService.update(mentoringCardId, mtrCardRequest);
        return "감사카드 수정이 완료되었습니다.";
    }

    @Operation(summary = "감사카드 삭제")
    @DeleteMapping("/from/{mentoringCardId}")
    public String deleteMtrCard(@PathVariable Long mentoringCardId) {
        mentoringCardService.delete(mentoringCardId);
        return "감사카드 삭제가 완료되었습니다.";
    }

    @Operation(summary = "나에게 온 감사카드 리스트 조회", description = "only 멘토")
    @GetMapping("/to")
    public List<MentoringCardToResponse> getMentoringCardListToMe() {
        return mentoringCardService.getMentoringCardListToMe();
    }
}
