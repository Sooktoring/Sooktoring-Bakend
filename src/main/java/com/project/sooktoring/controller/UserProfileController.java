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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.*;

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

    //프로필 등록 API 사용X -> 회원가입 시 자동으로 기본값으로 초기화 -> 이후 계속 수정만 가능

    @Operation(summary = "나의 프로필 수정", description = "나의 프로필 수정")
    @PutMapping(value = "/profile", consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public String updateMyUserProfile(@RequestPart @Validated UserProfileRequest userProfileRequest,
                                      @RequestPart MultipartFile file,
                                      @CurrentUser UserPrincipal currentUser) {
        userProfileService.update(userProfileRequest, file, currentUser.getUserId());
        return "프로필 수정이 완료되었습니다."; //나중에 DTO로 변경
    }

    @Operation(summary = "모든 이용자 프로필 조회", description = "모든 이용자 프로필 조회")
    @GetMapping("/profiles")
    public List<UserProfileResponse> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @Operation(summary = "특정 이용자 프로필 상세조회", description = "아이디 유효하지 않으면 null 반환")
    @GetMapping("/profiles/{userId}")
    public UserProfileResponse getUserProfile(@PathVariable Long userId) {
        return userProfileService.getUserProfile(userId);
    }
}
