package com.project.sooktoring.profile.controller;

import com.project.sooktoring.profile.dto.request.ProfileRequest;
import com.project.sooktoring.profile.dto.response.MentorProfileResponse;
import com.project.sooktoring.profile.dto.response.ProfileResponse;
import com.project.sooktoring.profile.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user/profiles")
@RequiredArgsConstructor
@Api(tags = "프로필 API")
public class ProfileController {

    private final ProfileService userProfileService;

    @Operation(summary = "나의 프로필 상세 조회")
    @GetMapping("/me")
    public ProfileResponse getMyProfile() {
        return userProfileService.getProfileDto();
    }

    //프로필 등록 API 사용X -> 회원가입 시 자동으로 기본값으로 초기화 -> 이후 계속 수정만 가능

    @Operation(summary = "나의 프로필 수정", description = "프로필 이미지 변경 시 MultipartFile 데이터 전달")
    @PutMapping(value = "/me")
    public String updateMyUserProfile(@RequestPart @Validated ProfileRequest profileRequest,
                                      @RequestPart(required = false) MultipartFile file) {
        userProfileService.update(profileRequest, file);
        return "프로필 수정이 완료되었습니다."; //나중에 DTO로 변경
    }

    @Operation(summary = "모든 이용자 프로필 리스트 조회")
    @GetMapping
    public List<ProfileResponse> getProfiles() {
        return userProfileService.getProfiles();
    }

    @Operation(summary = "특정 이용자 프로필 상세 조회")
    @GetMapping("/{profileId}")
    public ProfileResponse getProfile(@PathVariable Long profileId) {
        return userProfileService.getProfileDto(profileId);
    }

    @Operation(summary = "멘토들의 프로필 리스트 조회")
    @GetMapping("/mentors")
    public List<MentorProfileResponse> getMentorList() {
        return userProfileService.getMentorList();
    }

    @Operation(summary = "특정 멘토의 프로필 상세 조회")
    @GetMapping("/mentors/{profileId}")
    public MentorProfileResponse getMentor(@PathVariable Long profileId) {
        return userProfileService.getMentor(profileId);
    }
}
