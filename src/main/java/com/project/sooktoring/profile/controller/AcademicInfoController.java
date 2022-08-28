package com.project.sooktoring.profile.controller;

import com.project.sooktoring.profile.dto.request.AcademicInfoRequest;
import com.project.sooktoring.profile.dto.response.AcademicInfoResponse;
import com.project.sooktoring.profile.service.AcademicInfoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "학적정보 API")
@RequiredArgsConstructor
@RequestMapping("/academicInfo")
@RestController
public class AcademicInfoController {

    private final AcademicInfoService academicInfoService;

    @Operation(summary = "나의 학적정보 조회", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 나의 학적정보 조회된 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 학적정보")
    })
    @GetMapping("/me")
    public AcademicInfoResponse getMyAcademicInfo() {
        return academicInfoService.getMyAcademicInfo();
    }


    @Operation(summary = "나의 학적정보 변경", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 나의 학적정보 수정된 경우"),
            @ApiResponse(responseCode = "400", description = "멘토의 학적상태 재학 or 휴학으로 바꾸려고 하는 경우"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 엑세스 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 or 프로필 or 학적정보"),
            @ApiResponse(responseCode = "500", description = "이미지 파일 업로드 실패")
    })
    @PutMapping("/me")
    public ResponseEntity<Void> updateMyAcademicInfo(@RequestPart AcademicInfoRequest academicInfoRequest,
                                                     @RequestPart(required = false) MultipartFile file) {
        academicInfoService.update(academicInfoRequest, file);
        return ResponseEntity.status(OK).build();
    }
}
