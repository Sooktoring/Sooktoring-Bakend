package com.project.sooktoring.contest.controller;

import com.project.sooktoring.contest.dto.response.ContestListResponse;
import com.project.sooktoring.contest.service.ContestService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "공모전 API")
@RequiredArgsConstructor
@RequestMapping("/contests")
@RestController
public class ContestController {

    private final ContestService contestService;

    @GetMapping
    public List<ContestListResponse> getContestList() {
        return contestService.getContestList();
    }
}
