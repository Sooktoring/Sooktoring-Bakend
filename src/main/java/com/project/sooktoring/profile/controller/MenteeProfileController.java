package com.project.sooktoring.profile.controller;

import com.project.sooktoring.profile.dto.request.MenteeProfileRequest;
import com.project.sooktoring.profile.dto.response.MenteeProfileResponse;
import com.project.sooktoring.profile.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "멘티 프로필 API")
@RequiredArgsConstructor
@RequestMapping("/profile/mentees")
@RestController
public class MenteeProfileController {

    private final ProfileService profileService;

    @Operation(summary = "나의 프로필 상세 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 나의 프로필 상세 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "현재 이용자가 멘토인 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필")
    })
    @GetMapping("/me")
    public MenteeProfileResponse getMyMenteeProfile() {
        return profileService.getMyMenteeProfile();
    }


    @Operation(summary = "특정 멘티 이용자 프로필 상세 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 특정 멘티 이용자 프로필 상세 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 (멘티) 프로필")
    })
    @GetMapping("/{profileId}")
    public MenteeProfileResponse getMenteeProfile(@Parameter(description = "프로필 id") @PathVariable Long profileId) {
        return profileService.getMenteeProfile(profileId);
    }


    @Operation(summary = "나의 프로필 수정", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 나의 프로필 수정된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "403", description = "현재 이용자가 멘토인 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로필 or 대외활동"),
            @ApiResponse(responseCode = "500", description = "이미지 파일 업로드 실패")
    })
    @PutMapping("/me")
    public ResponseEntity<Void> updateMyMenteeProfile(@RequestPart MenteeProfileRequest menteeProfileRequest,
                                                      @RequestPart(required = false) MultipartFile file) {
        profileService.updateMyMenteeProfile(menteeProfileRequest, file);
        return ResponseEntity.status(OK).build();
    }
}
