package com.project.sooktoring.profile.controller;

import com.project.sooktoring.profile.dto.request.MenteeProfileRequest;
import com.project.sooktoring.profile.dto.response.MenteeProfileResponse;
import com.project.sooktoring.profile.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "나의 프로필 상세 조회 - 멘티")
    @GetMapping("/me")
    public MenteeProfileResponse getMyMenteeProfile() {
        return profileService.getMyMenteeProfile();
    }

    @Operation(summary = "특정 이용자 프로필 상세 조회 - 멘티")
    @GetMapping("/{profileId}")
    public MenteeProfileResponse getMenteeProfile(@PathVariable Long profileId) {
        return profileService.getMenteeProfile(profileId);
    }

    @Operation(summary = "나의 프로필 수정 - 멘티")
    @PutMapping("/me")
    public ResponseEntity<Void> updateMyMenteeProfile(@RequestPart MenteeProfileRequest menteeProfileRequest,
                                                      @RequestPart(required = false) MultipartFile file) {
        profileService.updateMyMenteeProfile(menteeProfileRequest, file);
        return ResponseEntity.status(OK).build();
    }
}
