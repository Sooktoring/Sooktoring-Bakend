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

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/clubs")
@RestController
public class ClubController {

    private final ClubService clubService;

    //현재 최신순 조회만 구현
    @GetMapping
    public List<ClubListResponse> getClubListByOrder(@RequestParam ClubKind kind,
                                                     @RequestParam String order) {
        //교내 or 교외 동아리 리스트 조회
        if (kind == ClubKind.IN) {
//            if (order.equals("new")) {}
            return clubService.getClubInListByNewOrder();
        }
        else {
//            if (order.equals("new")) {}
            return clubService.getClubOutListByNewOrder();
        }
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
    public ResponseEntity<Void> saveClub(@RequestBody ClubRequest clubRequest) {
        clubService.save(clubRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{clubId}")
    public ResponseEntity<Void> updateClub(@PathVariable Long clubId,
                                           @RequestBody ClubRequest clubRequest) {
        clubService.update(clubId, clubRequest);
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long clubId) {
        clubService.delete(clubId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
