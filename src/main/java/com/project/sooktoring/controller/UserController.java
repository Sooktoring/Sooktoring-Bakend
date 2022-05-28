package com.project.sooktoring.controller;

import com.project.sooktoring.auth.dto.response.AuthExResponse;
import com.project.sooktoring.auth.user.CurrentUser;
import com.project.sooktoring.auth.user.UserPrincipal;
import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "유저 정보 조회", description = "로그인 상태의 유저 정보 조회", responses = {
            @ApiResponse(
                    responseCode = "400",
                    description = "만료된 엑세스 토큰이 전달된 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthExResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "유효하지 않은 JWT 토큰 전달된 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthExResponse.class)
                    )
            )
    })
    @GetMapping("/me")
    public User getUser(@CurrentUser UserPrincipal currentUser) {
        Optional<User> userOptional = userRepository.findById(currentUser.getUserId());
        return userOptional.orElse(null);
    }
}
