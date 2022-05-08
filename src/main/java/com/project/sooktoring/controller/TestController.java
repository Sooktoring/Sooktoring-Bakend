package com.project.sooktoring.controller;

import com.project.sooktoring.auth.client.UserPrincipal;
import com.project.sooktoring.domain.User;
import com.project.sooktoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;

    @GetMapping("/test")
    public String test() {
        //로그인한 사용자 정보
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal.getProviderId() = " + principal.getProviderId());
        Optional<User> user = userRepository.findByProviderId(principal.getProviderId());
        user.ifPresent(u -> System.out.println("user = " + u.getEmail()));
        return "hello world";
    }
}
