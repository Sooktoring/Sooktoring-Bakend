package com.project.sooktoring.controller;

import com.project.sooktoring.auth.user.CurrentUser;
import com.project.sooktoring.auth.user.UserPrincipal;
import com.project.sooktoring.dto.request.UserProfileRequest;
import com.project.sooktoring.dto.response.UserProfileResponse;
import com.project.sooktoring.service.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "유저 프로필 API")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "나의 프로필 상세조회", description = "나의 프로필 상세조회")
    @GetMapping("/profile")
    public UserProfileResponse getMyUserProfile(@CurrentUser UserPrincipal currentUser) {
        return userProfileService.getUserProfile(currentUser.getUserId());
    }

    @Operation(summary = "나의 프로필 등록", description = "나의 프로필 등록 - 로그인/회원가입 후 가능")
    @PostMapping("/profile")
    public String saveMyUserProfile(@RequestBody @Validated UserProfileRequest userProfileRequest,
                                  @CurrentUser UserPrincipal currentUser) {
        userProfileService.save(userProfileRequest, currentUser.getUserId());
        return "프로필 설정이 완료되었습니다."; //나중에 DTO로 변경
    }

    @Operation(summary = "나의 프로필 수정", description = "나의 프로필 수정")
    @PutMapping("/profile")
    public String updateMyUserProfile(@RequestBody @Validated UserProfileRequest userProfileRequest,
                                  @CurrentUser UserPrincipal currentUser) {
        userProfileService.update(userProfileRequest, currentUser.getUserId());
        return "프로필 수정이 완료되었습니다."; //나중에 DTO로 변경
    }

    @Operation(summary = "모든 이용자 프로필 조회", description = "모든 이용자 프로필 조회")
    @GetMapping("/profiles")
    public List<UserProfileResponse> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @Operation(summary = "특정 이용자 프로필 상세조회", description = "아이디 유효하지 않으면 null 반환")
    @GetMapping("/profile/{userId}")
    public UserProfileResponse getUserProfile(@PathVariable Long userId) {
        return userProfileService.getUserProfile(userId);
    }
}
