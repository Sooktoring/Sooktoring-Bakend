package com.project.sooktoring.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ApiOperation(value = "테스트용 컨트롤러", notes = "서버 연결 확인용 'hello world!' 반환")
    @GetMapping("/test")
    public String test() {
        return "hello world!";
    }
}
