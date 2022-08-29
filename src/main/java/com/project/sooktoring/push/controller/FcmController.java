package com.project.sooktoring.push.controller;

import com.project.sooktoring.push.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//FCM 테스트 컨트롤러 (나중에 지울 예정)
@RequiredArgsConstructor
@RequestMapping("/push")
@RestController
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/noti")
    public String pushNotification() throws IOException {
        fcmService.sendMessageTo("",
                "hi", "hello world! wow~~~~~~~~~~~~~~~!!");
        return "푸시 알림 완료";
    }
}
