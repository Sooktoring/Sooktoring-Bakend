package com.project.sooktoring.profile.controller;

import com.project.sooktoring.profile.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "프로필 API")
@RequiredArgsConstructor
@RequestMapping("/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "닉네임 중복여부 체크", description = "나를 제외한 이용자 중 중복되는 닉네임 있는지 체크")
    @GetMapping
    public ResponseEntity<Void> checkNickName(@RequestParam String nickName) {
        profileService.checkNickName(nickName);
        return ResponseEntity.status(OK).build();
    }
}
