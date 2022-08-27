package com.project.sooktoring.profile.controller;

import com.project.sooktoring.profile.dto.request.MentorProfileRequest;
import com.project.sooktoring.profile.dto.response.MentorProfileListResponse;
import com.project.sooktoring.profile.dto.response.MentorProfileResponse;
import com.project.sooktoring.profile.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags = "멘토 프로필 API")
@RequiredArgsConstructor
@RequestMapping("/profile/mentors")
@RestController
public class MentorProfileController {

    private final ProfileService profileService;

    @Operation(summary = "나의 프로필 상세 조회")
    @GetMapping("/me")
    public MentorProfileResponse getMyMentorProfile() {
        return profileService.getMyMentorProfile();
    }

    @Operation(summary = "특정 이용자 프로필 상세 조회")
    @GetMapping("/{profileId}")
    public MentorProfileResponse getMentorProfile(@PathVariable Long profileId) {
        return profileService.getMentorProfile(profileId);
    }

    @Operation(summary = "멘토들의 프로필 리스트 조회")
    @GetMapping
    public List<MentorProfileListResponse> getMentorProfiles() {
        return profileService.getMentorProfileDtoList();
    }

    @Operation(summary = "나의 프로필 수정")
    @PutMapping("/me")
    public ResponseEntity<Void> updateMyMentorProfile(@RequestPart MentorProfileRequest mentorProfileRequest,
                                                      @RequestPart(required = false) MultipartFile file) {
        profileService.updateMyMentorProfile(mentorProfileRequest, file);
        return ResponseEntity.status(OK).build();
    }
}
