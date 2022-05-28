package com.project.sooktoring.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "테스트 API")
public class TestController {

    @Operation(summary = "테스트용 컨트롤러", description = "서버 연결 확인용 'hello world!' 반환")
    @GetMapping("/test")
    public String test() {
        return "hello world!";
    }

    /*
    @Operation(summary = "멘티용 컨트롤러", description = "멘티 권한 가진 경우만 접근 가능")
    @GetMapping("/mentee")
    public String mentee() {
        return "hi mentee!";
    }

    @Operation(summary = "멘토용 컨트롤러", description = "멘토 권한 가진 경우만 접근 가능")
    @GetMapping("/mentor")
    public String mentor() {
        return "hi mentor!";
    }
    */
}
