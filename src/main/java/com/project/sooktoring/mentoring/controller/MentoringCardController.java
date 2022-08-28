package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.dto.request.MentoringCardRequest;
import com.project.sooktoring.mentoring.dto.response.MentoringCardFromResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringCardToResponse;
import com.project.sooktoring.mentoring.service.MentoringCardService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "내가 쓴 감사카드 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 감사카드 리스트 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필")
    })
    @GetMapping("/from")
    public List<MentoringCardFromResponse> getMentoringCardListFromMe() {
        return mentoringCardService.getMentoringCardListFromMe();
    }


    @Operation(summary = "내가 쓴 감사카드 상세 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 감사카드 상세 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링 or 멘토링 카드")
    })
    @GetMapping("/from/{mentoringCardId}")
    public MentoringCardFromResponse getMentoringCardFromMe(@Parameter(description = "멘토링 카드 id") @PathVariable Long mentoringCardId) {
        return mentoringCardService.getMentoringCardFromMe(mentoringCardId);
    }


    @Operation(summary = "나에게 온 감사카드 리스트 조회", description = "only 멘토", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 감사카드 리스트 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필")
    })
    @GetMapping("/to")
    public List<MentoringCardToResponse> getMentoringCardListToMe() {
        return mentoringCardService.getMentoringCardListToMe();
    }


    @Operation(summary = "나에게 온 감사카드 상세 조회", description = "only 멘토", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 감사카드 상세 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링 or 멘토링 카드")
    })
    @GetMapping("/to/{mentoringCardId}")
    public MentoringCardToResponse getMentoringCardToMe(@Parameter(description = "멘토링 카드 id") @PathVariable Long mentoringCardId) {
        return mentoringCardService.getMentoringCardToMe(mentoringCardId);
    }


    @Operation(summary = "감사카드 작성", description = "해당 멘토링 종료 상태일 때에만 작성 가능", responses = {
            @ApiResponse(responseCode = "201", description = "정상적으로 감사카드 작성된 경우"),
            @ApiResponse(responseCode = "400", description = "해당 멘토링에 대한 멘토링 카드 이미 존재 (카드 중복 작성)"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근 or 멘토링 카드 작성 권한 없음 (멘토링 미종료)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링")
    })
    @PostMapping("/from/{mentoringId}")
    public ResponseEntity<Void> saveMentoringCard(@Parameter(description = "멘토링 id") @PathVariable Long mentoringId,
                                                  @RequestBody @Validated MentoringCardRequest mentoringCardRequest) {
        mentoringCardService.save(mentoringId, mentoringCardRequest);
        return ResponseEntity.status(CREATED).build();
    }


    @Operation(summary = "감사카드 수정", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 감사카드 수정된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링 or 멘토링 카드")
    })
    @PutMapping("/from/{mentoringCardId}")
    public ResponseEntity<Void> updateMentoringCard(@Parameter(description = "멘토링 카드 id") @PathVariable Long mentoringCardId,
                                                    @RequestBody @Validated MentoringCardRequest mentoringCardRequest) {
        mentoringCardService.update(mentoringCardId, mentoringCardRequest);
        return ResponseEntity.status(OK).build();
    }


    @Operation(summary = "감사카드 삭제", responses = {
            @ApiResponse(responseCode = "204", description = "정상적으로 감사카드 삭제된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없는 멘토링에 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 멘토링 or 멘토링 카드")
    })
    @DeleteMapping("/from/{mentoringCardId}")
    public ResponseEntity<Void> deleteMentoringCard(@Parameter(description = "멘토링 카드 id") @PathVariable Long mentoringCardId) {
        mentoringCardService.delete(mentoringCardId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
