package com.project.sooktoring.club.controller;

import com.project.sooktoring.club.dto.request.ClubRequest;
import com.project.sooktoring.club.dto.response.ClubListResponse;
import com.project.sooktoring.club.dto.response.ClubRecruitListResponse;
import com.project.sooktoring.club.dto.response.ClubResponse;
import com.project.sooktoring.club.enumerate.ClubKind;
import com.project.sooktoring.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/clubs")
@RestController
public class ClubController { //현재 최신순 조회만 구현

    private final ClubService clubService;

    //교내 or 교외 동아리 리스트 조회
    @GetMapping
    public List<ClubListResponse> getClubListByOrder(@RequestParam ClubKind kind,
                                                     @RequestParam String order) {
//        if (order.equals("new")) {}
        return clubService.getClubListByNewOrder(kind);
    }

    @GetMapping("/recruit")
    public List<ClubRecruitListResponse> getClubRecruitList(@RequestParam String order) {
//        if (order.equals("new")) {}
        return clubService.getClubRecruitListByNewOrder();
    }

    @GetMapping("/{clubId}")
    public ClubResponse getClub(@PathVariable Long clubId) {
        return clubService.getClub(clubId);
    }

    @PostMapping
    public ResponseEntity<Void> saveClub(@RequestPart ClubRequest clubRequest,
                                         @RequestPart(required = false) MultipartFile file) {
        clubService.save(clubRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{clubId}")
    public ResponseEntity<Void> updateClub(@PathVariable Long clubId,
                                           @RequestPart ClubRequest clubRequest,
                                           @RequestPart(required = false) MultipartFile file) {
        clubService.update(clubId, clubRequest, file);
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long clubId) {
        clubService.delete(clubId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
