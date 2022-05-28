package com.project.sooktoring.controller;

import io.swagger.annotations.Api;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Api(tags = "채팅 API")
public class ChatController {

    @MessageMapping("/hello")
    @SendTo("/sub/hello")
    public String greeting(String message) throws Exception {
        return "Hello ! " + message;
    }

}