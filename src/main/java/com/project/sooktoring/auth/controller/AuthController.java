package com.project.sooktoring.auth.controller;

import com.project.sooktoring.auth.dto.AuthRequest;
import com.project.sooktoring.auth.dto.AuthResponse;
import com.project.sooktoring.auth.dto.TokenRequest;
import com.project.sooktoring.auth.dto.TokenResponse;
import com.project.sooktoring.auth.service.AuthService;
import com.project.sooktoring.auth.service.GoogleAuthService;
import com.project.sooktoring.dto.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final AuthService authService;

    /**
     * GOOGLE 소셜 로그인 기능
     */
    @ApiOperation(value = "구글 로그인", notes = "구글 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 반환")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> googleAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(googleAuthService.login(authRequest));
    }

    /**
     * appToken 갱신
     */
    @ApiOperation(value = "appToken, refreshToken 갱신", notes = "appToken 만료에 따른 appToken, refreshToken 갱신")
    @GetMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken (@RequestBody TokenRequest tokenRequest) {
        return ApiResponse.success(authService.refresh(tokenRequest));
    }
}
