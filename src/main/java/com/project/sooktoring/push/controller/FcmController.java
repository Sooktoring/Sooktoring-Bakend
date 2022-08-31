package com.project.sooktoring.push.controller;

import com.project.sooktoring.push.service.FcmService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "푸시알림 API")
@RequiredArgsConstructor
@RequestMapping("/push")
@RestController
public class FcmController {

    private final FcmService fcmService;

    @Operation(summary = "푸시 알림 전송 테스트 (삭제 예정)")
    @PostMapping("/test")
    public ResponseEntity<Void> pushNotification(@Parameter(description = "파이어베이스 등록 토큰") @RequestParam String targetToken) throws IOException {
        fcmService.sendMessageTo(targetToken, "hi", "hello world! wow~~~~~~~~~~~~~~~!!");
        return ResponseEntity.status(OK).build();
    }

    @Operation(summary = "현재 유저의 파이어베이스 등록 토큰 업데이트", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 파이어베이스 등록 토큰 업데이트된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
    })
    @PutMapping
    public ResponseEntity<Void> updateFcmToken(@Parameter(description = "파이어베이스 등록 토큰") @RequestParam String fcmToken) {
        fcmService.updateFcmToken(fcmToken);
        return ResponseEntity.status(OK).build();
    }
}
