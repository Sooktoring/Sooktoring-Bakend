package com.project.sooktoring.controller;

import com.project.sooktoring.auth.user.CurrentUser;
import com.project.sooktoring.auth.user.UserPrincipal;
import com.project.sooktoring.domain.User;
import com.project.sooktoring.repository.UserRepository;
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

    @GetMapping("/me")
    public User getUser(@CurrentUser UserPrincipal currentUser) {
        Optional<User> userOptional = userRepository.findByProviderId(currentUser.getProviderId());
        return userOptional.orElse(null);
    }
}
