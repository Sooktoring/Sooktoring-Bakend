package com.project.sooktoring.controller;

import com.project.sooktoring.auth.common.ExJson;
import com.project.sooktoring.auth.user.CurrentUser;
import com.project.sooktoring.auth.user.UserPrincipal;
import com.project.sooktoring.domain.User;
import com.project.sooktoring.repository.UserRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @ApiOperation(value = "사용자 정보 조회", notes = "로그인 상태의 사용자 정보 조회")
    @ApiResponses({
            @ApiResponse(code = 400, message = "만료된 access token",
                    examples = @Example(value = {
                            @ExampleProperty(mediaType = "application/json",
                                    value = ExJson.EXPIRED_ACCESS_TOKEN),
                    })),
            @ApiResponse(code = 500, message = "유효하지 않은 JWT token",
                    examples = @Example(value = {
                            @ExampleProperty(mediaType = "application/json",
                                    value = ExJson.INVALID_JWT_TOKEN),
                    })),
    })
    @GetMapping("/me")
    public User getUser(@CurrentUser UserPrincipal currentUser) {
        Optional<User> userOptional = userRepository.findByProviderId(currentUser.getProviderId());
        return userOptional.orElse(null);
    }
}
