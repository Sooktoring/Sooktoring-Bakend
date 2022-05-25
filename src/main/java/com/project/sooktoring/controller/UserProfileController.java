package com.project.sooktoring.controller;

import com.project.sooktoring.auth.user.CurrentUser;
import com.project.sooktoring.auth.user.UserPrincipal;
import com.project.sooktoring.dto.request.UserProfileRequest;
import com.project.sooktoring.dto.response.UserProfileResponse;
import com.project.sooktoring.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/profile")
    public UserProfileResponse getUserProfile(@CurrentUser UserPrincipal currentUser) {
        return userProfileService.getUserProfile(currentUser.getUserId());
    }

    @GetMapping("/profiles")
    public List<UserProfileResponse> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PostMapping("/profile")
    public String saveUserProfile(@RequestBody UserProfileRequest userProfileRequest,
                                  @CurrentUser UserPrincipal currentUser) {
        userProfileService.save(userProfileRequest, currentUser.getUserId());
        return "프로필 설정이 완료되었습니다."; //나중에 DTO로 변경
    }

    @PutMapping("/profile")
    public String updateUserProfile(@RequestBody UserProfileRequest userProfileRequest,
                                  @CurrentUser UserPrincipal currentUser) {
        userProfileService.update(userProfileRequest, currentUser.getUserId());
        return "프로필 수정이 완료되었습니다."; //나중에 DTO로 변경
    }
}
