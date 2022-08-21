package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.dto.request.MentoringCardRequest;
import com.project.sooktoring.mentoring.dto.response.MentoringCardFromResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringCardToResponse;
import com.project.sooktoring.mentoring.service.MentoringCardService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

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

    @Operation(summary = "나에게 온 감사카드 리스트 조회", description = "only 멘토")
    @GetMapping("/to")
    public List<MentoringCardToResponse> getMentoringCardListToMe() {
        return mentoringCardService.getMentoringCardListToMe();
    }

    @Operation(summary = "감사카드 작성", description = "해당 멘토링 종료 상태일 때에만 작성 가능")
    @PostMapping("/from/{mentoringId}")
    public ResponseEntity<Void> saveMentoringCard(@PathVariable Long mentoringId,
                                                  @RequestBody @Validated MentoringCardRequest mentoringCardRequest) {
        mentoringCardService.save(mentoringId, mentoringCardRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @Operation(summary = "감사카드 수정")
    @PutMapping("/from/{mentoringCardId}")
    public ResponseEntity<Void> updateMentoringCard(@PathVariable Long mentoringCardId,
                                                    @RequestBody @Validated MentoringCardRequest mentoringCardRequest) {
        mentoringCardService.update(mentoringCardId, mentoringCardRequest);
        return ResponseEntity.status(OK).build();
    }

    @Operation(summary = "감사카드 삭제")
    @DeleteMapping("/from/{mentoringCardId}")
    public ResponseEntity<Void> deleteMentoringCard(@PathVariable Long mentoringCardId) {
        mentoringCardService.delete(mentoringCardId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
