package com.project.sooktoring.auth.controller;

import com.project.sooktoring.auth.dto.request.AuthRequest;
import com.project.sooktoring.auth.dto.request.TokenRequest;
import com.project.sooktoring.auth.dto.response.AuthExResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "인증 API")
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final AuthService authService;

    /**
     * GOOGLE 소셜 로그인 기능
     */
    @Operation(summary = "구글 로그인", description = "구글 아이디 토큰을 이용하여 사용자 정보 받아 저장하고 엑세스 토큰 반환", responses = {
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 구글 아이디 토큰",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthExResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "구글 리소스 서버 접근 거부",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthExResponse.class)
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> googleAuthRequest(@RequestBody AuthRequest authRequest,
                                                          HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(googleAuthService.login(authRequest, request));
    }

    /**
     * accessToken 갱신
     */
    @Operation(summary = "엑세스, 리프레시 토큰 갱신", description = "엑세스 토큰 만료에 따른 엑세스, 리프레시 토큰 갱신", responses = {
            @ApiResponse(
                    responseCode = "400",
                    description = "만료된 리프레시 토큰",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthExResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "유효하지 않은 JWT 토큰",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthExResponse.class)
                    )
            )
    })
    @GetMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken (@RequestBody TokenRequest tokenRequest,
                                                       HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.refresh(tokenRequest, request));
    }
}
