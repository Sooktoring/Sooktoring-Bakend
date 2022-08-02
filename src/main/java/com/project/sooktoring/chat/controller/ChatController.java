package com.project.sooktoring.chat.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Api(tags = "채팅 API")
public class ChatController {

    @MessageMapping("/hello")
    @SendTo("/sub/hello")
    public String greeting(String message) throws Exception {
        return "Hello ! " + message;
    }

}