package com.project.sooktoring.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "API 정보 제공하는 Controller")
@RestController
@RequestMapping("/api")
public class TestController {

    @ApiOperation(value = "hello 메시지 반환하는 메서드")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "x", value = "x 값", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "y", value = "y 값", required = true, dataType = "int", paramType = "query")
    })
    @ApiOperation(value = "파라미터의 값을 뺀 결과를 반환하는 메서드")
    @GetMapping("/minus/{x}")
    public int minus(@PathVariable int x, @RequestParam int y) {
        return x - y;
    }
}
