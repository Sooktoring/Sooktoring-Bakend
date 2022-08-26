package com.project.sooktoring.profile.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "마이페이지 API")
@RequiredArgsConstructor
@RequestMapping("/myPages")
@RestController
public class MyPageController {
}
