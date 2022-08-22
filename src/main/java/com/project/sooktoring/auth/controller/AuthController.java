package com.project.sooktoring.auth.controller;

import com.project.sooktoring.common.exception.ErrorResponse;
import com.project.sooktoring.auth.dto.request.AuthRequest;
import com.project.sooktoring.auth.dto.request.TokenRequest;
import com.project.sooktoring.auth.dto.response.AuthResponse;
import com.project.sooktoring.auth.dto.response.TokenResponse;
import com.project.sooktoring.auth.service.AuthService;
import com.project.sooktoring.auth.service.GoogleAuthService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "유저 인증 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final AuthService authService;

    @Operation(summary = "구글 로그인", description = "구글 아이디 토큰을 이용하여 사용자 정보받아 저장하고 JWT 반환", responses = {
            @ApiResponse(
                    responseCode = "403",
                    description = "유효하지 않은 구글 아이디 토큰 or 구글 리소스 서버 접근 거부",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping("/login")
    public AuthResponse issue(@RequestBody AuthRequest authRequest) {
        return googleAuthService.login(authRequest);
    }

    @Operation(summary = "JWT 갱신", description = "엑세스 토큰 만료에 따른 엑세스, 리프레시 토큰 갱신", responses = {
            @ApiResponse(
                    responseCode = "403",
                    description = "만료된 리프레시 토큰",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping("/refresh")
    public TokenResponse reissue(@RequestBody TokenRequest tokenRequest) {
        return authService.refresh(tokenRequest);
    }
}
