package com.project.sooktoring.controller;

import com.project.sooktoring.domain.chat.ChatMessage;
import com.project.sooktoring.domain.chat.Greeting;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {

    @MessageMapping("/hello")
    @SendTo("/sub/hello")
    public Greeting greeting(ChatMessage message) throws Exception {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/message")
    @SendTo("/sub/message")
    public ChatMessage chat(ChatMessage chatMessage) throws Exception {
        return new ChatMessage(chatMessage.getName(), chatMessage.getMessage());
    }

}