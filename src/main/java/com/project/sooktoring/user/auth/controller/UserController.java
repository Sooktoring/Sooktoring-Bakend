package com.project.sooktoring.user.auth.controller;

import com.project.sooktoring.common.exception.ErrorResponse;
import com.project.sooktoring.user.auth.util.CurrentUser;
import com.project.sooktoring.user.auth.util.UserPrincipal;
import com.project.sooktoring.user.auth.domain.User;
import com.project.sooktoring.user.auth.repository.UserRepository;
import com.project.sooktoring.user.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "유저 API")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "로그인 상태의 유저 정보 조회", responses = {
            @ApiResponse(
                    responseCode = "403",
                    description = "유효하지 않은 엑세스 토큰이 전달된 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/me")
    public User getUser(@CurrentUser UserPrincipal currentUser) {
        Optional<User> userOptional = userRepository.findById(currentUser.getUserId());
        return userOptional.orElse(null);
    }

    @Operation(summary = "유저 탈퇴")
    @DeleteMapping("/me")
    public String withdraw(@CurrentUser UserPrincipal currentUser) {
        userService.withdrawById(currentUser.getUserId());
        return "회원 탈퇴가 완료되었습니다.";
    }
}
