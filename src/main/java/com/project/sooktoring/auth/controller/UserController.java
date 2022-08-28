package com.project.sooktoring.auth.controller;

import com.project.sooktoring.auth.dto.response.UserNameResponse;
import com.project.sooktoring.common.utils.UserUtil;
import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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
            @ApiResponse(responseCode = "200", description = "정상적으로 현재 유저 정보 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
    })
    @GetMapping("/me")
    public User getUser() {
        return userUtil.getCurrentUser();
    }


    @Operation(summary = "현재 로그인한 유저 이름 조회", description = "구글 계정 이름 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 현재 유저 이름 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
    })
    @GetMapping("/name")
    public UserNameResponse getUserName() {
        User user = userUtil.getCurrentUser();
        return UserNameResponse.builder()
                .userName(user.getName())
                .build();
    }


    @Operation(summary = "유저 탈퇴", responses = {
            @ApiResponse(responseCode = "204", description = "정상적으로 유저 탈퇴 로직 수행된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 or 프로필 or 학적정보")
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> withdraw() {
        userService.withdraw();
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
