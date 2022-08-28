package com.project.sooktoring.club.controller;

import com.project.sooktoring.club.dto.request.ClubRequest;
import com.project.sooktoring.club.dto.response.ClubListResponse;
import com.project.sooktoring.club.dto.response.ClubRecruitListResponse;
import com.project.sooktoring.club.dto.response.ClubResponse;
import com.project.sooktoring.club.enumerate.ClubKind;
import com.project.sooktoring.club.service.ClubService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "동아리 API")
@RequiredArgsConstructor
@RequestMapping("/clubs")
@RestController
public class ClubController { //현재 최신순 조회만 구현

    private final ClubService clubService;

    @Operation(summary = "교내 or 교외 동아리 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 동아리 리스트 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰")
    })
    @GetMapping
    public List<ClubListResponse> getClubListByOrder(@Parameter(description = "동아리 종류 (IN - 교내, OUT - 교외)") @RequestParam ClubKind kind,
                                                     @Parameter(description = "리스트 조회 순서 (new - 최신순, like - 인기순)") @RequestParam String order) {
//        if (order.equals("new")) {}
        return clubService.getClubListByNewOrder(kind);
    }


    @Operation(summary = "모집중 동아리 리스트 조회", description = "일단 현재는 모집중 여부 isRecruit 필드로 체크", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 동아리 리스트 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰")
    })
    @GetMapping("/recruit")
    public List<ClubRecruitListResponse> getClubRecruitList(@Parameter(description = "리스트 조회 순서 (new - 최신순, like - 인기순)") @RequestParam String order) {
//        if (order.equals("new")) {}
        return clubService.getClubRecruitListByNewOrder();
    }


    @Operation(summary = "동아리 상세 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 동아리 상세 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 동아리")
    })
    @GetMapping("/{clubId}")
    public ClubResponse getClub(@Parameter(description = "동아리 id") @PathVariable Long clubId) {
        return clubService.getClub(clubId);
    }


    @Operation(summary = "동아리 등록", description = "MultipartFile 타입의 파일 전달", responses = {
            @ApiResponse(responseCode = "201", description = "정상적으로 동아리 등록된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "500", description = "이미지 파일 업로드 실패")
    })
    @PostMapping
    public ResponseEntity<Void> saveClub(@RequestPart ClubRequest clubRequest,
                                         @RequestPart(required = false) MultipartFile file) {
        clubService.save(clubRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "동아리 수정", description = "MultipartFile 타입의 파일 전달", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 동아리 수정된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 동아리 or 동아리 URL"),
            @ApiResponse(responseCode = "500", description = "이미지 파일 업로드 실패")
    })
    @PutMapping("/{clubId}")
    public ResponseEntity<Void> updateClub(@Parameter(description = "동아리 id") @PathVariable Long clubId,
                                           @RequestPart ClubRequest clubRequest,
                                           @RequestPart(required = false) MultipartFile file) {
        clubService.update(clubId, clubRequest, file);
        return ResponseEntity.status(OK).build();
    }


    @Operation(summary = "동아리 삭제", responses = {
            @ApiResponse(responseCode = "204", description = "정상적으로 동아리 삭제된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 동아리")
    })
    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClub(@Parameter(description = "동아리 id") @PathVariable Long clubId) {
        clubService.delete(clubId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
