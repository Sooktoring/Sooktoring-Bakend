package com.project.sooktoring.profile.controller;

import com.project.sooktoring.profile.service.ProfileService;
import io.swagger.annotations.Api;
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

    @GetMapping
    public ResponseEntity<Void> checkNickName(@RequestParam String nickName) {
        profileService.checkNickName(nickName);
        return ResponseEntity.status(OK).build();
    }
}
