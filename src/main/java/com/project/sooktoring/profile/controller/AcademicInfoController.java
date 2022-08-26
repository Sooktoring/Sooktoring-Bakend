package com.project.sooktoring.profile.controller;

import com.project.sooktoring.profile.dto.request.AcademicInfoRequest;
import com.project.sooktoring.profile.dto.response.AcademicInfoResponse;
import com.project.sooktoring.profile.service.AcademicInfoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "학적정보 API")
@RequiredArgsConstructor
@RequestMapping("/academicInfos")
@RestController
public class AcademicInfoController {

    private final AcademicInfoService academicInfoService;

    @Operation(summary = "처음 학적정보 입력 시 기존 정보 조회")
    @GetMapping("/me")
    public AcademicInfoResponse getMyAcademicInfo() {
        return academicInfoService.getMyAcademicInfo();
    }

    @Operation(summary = "학적정보 변경")
    @PutMapping("/me")
    public ResponseEntity<Void> updateMyAcademicInfo(@RequestPart AcademicInfoRequest academicInfoRequest,
                                                   @RequestPart(required = false) MultipartFile file) {
        academicInfoService.update(academicInfoRequest, file);
        return ResponseEntity.status(OK).build();
    }
}
