package com.project.sooktoring.user.controller;

import com.project.sooktoring.common.exception.ErrorResponse;
import com.project.sooktoring.common.utils.UserUtil;
import com.project.sooktoring.user.domain.User;
import com.project.sooktoring.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "유저 API")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserUtil userUtil;
    private final UserService userService;

    //나중에 지울 예정
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
    public User getUser() {
        return userUtil.getCurrentUser();
    }

    @Operation(summary = "유저 탈퇴")
    @DeleteMapping("/me")
    public ResponseEntity<Void> withdraw() {
        userService.withdraw();
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
