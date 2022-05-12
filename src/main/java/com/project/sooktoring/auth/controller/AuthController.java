package com.project.sooktoring.auth.controller;

import com.project.sooktoring.auth.common.ExJson;
import com.project.sooktoring.auth.dto.*;
import com.project.sooktoring.auth.service.AuthService;
import com.project.sooktoring.auth.service.GoogleAuthService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final AuthService authService;
    private String googleAccessToken;

    /**
     * GOOGLE 소셜 로그인 기능
     */
    @ApiOperation(value = "구글 로그인", notes = "구글 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱 토큰 반환")
    @ApiResponses({
            @ApiResponse(code = 400, message = "유효하지 않은 구글 엑세스 토큰",
                    examples = @Example(value = {
                            @ExampleProperty(mediaType = "application/json",
                                    value = ExJson.GOOGLE_ACCESS_TOKEN),
                    })),
            @ApiResponse(code = 500, message = "구글 리소스 서버 접근 거부",
                    examples = @Example(value = {
                            @ExampleProperty(mediaType = "application/json",
                                    value = ExJson.GOOGLE_RESOURCE_SERVER),
                    })),
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> googleAuthRequest(@RequestBody AuthRequest authRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(googleAuthService.login(authRequest));
    }

    /**
     * appToken 갱신
     */
    @ApiOperation(value = "appToken, refreshToken 갱신", notes = "appToken 만료에 따른 appToken, refreshToken 갱신")
    @ApiResponses({
            @ApiResponse(code = 400, message = "만료되거나 유효하지 않은 리프레시 토큰",
                    examples = @Example(value = {
                            @ExampleProperty(mediaType = "application/json",
                                    value = ExJson.EXPIRED_REFRESH_TOKEN),
                            @ExampleProperty(mediaType = "application/json",
                                    value = ExJson.INVALID_REFRESH_TOKEN),
                    })),
            @ApiResponse(code = 500, message = "유효하지 않은 JWT 토큰",
                    examples = @Example(value = {
                            @ExampleProperty(mediaType = "application/json",
                                    value = ExJson.INVALID_JWT_TOKEN),
                    })),
    })
    @GetMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken (@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.refresh(tokenRequest));
    }
}
