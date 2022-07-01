package com.project.sooktoring.controller;

import com.project.sooktoring.domain.ChatRoom;
import com.project.sooktoring.service.ChatService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Api(tags = "채팅 API")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/room")
    public ChatRoom createRoom(@RequestBody String name) {
        return chatService.createRoom(name);
    }

    @GetMapping("/room")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

}