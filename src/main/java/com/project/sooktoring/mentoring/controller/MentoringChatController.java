package com.project.sooktoring.mentoring.controller;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.service.MentoringChatService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "멘토링 채팅 API")
@RequiredArgsConstructor
@RequestMapping("/mentoring/chat")
@RestController
public class MentoringChatController {

    private final MentoringChatService mentoringChatService;

    @Operation(summary = "채팅방 목록 조회")
    @GetMapping
    public List<Mentoring> getMyChatRoomList() {
        return mentoringChatService.getMyChatRoomList(); //**
    }
}
