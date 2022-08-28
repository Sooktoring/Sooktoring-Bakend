package com.project.sooktoring.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.sooktoring.profile.enumerate.AcademicStatus;
import com.project.sooktoring.profile.enumerate.Major;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.YearMonth;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

@Schema(description = "학적정보 조회 시 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademicInfoResponse {

    @Schema(description = "학적정보 id", example = "1")
    private Long academicInfoId;

    @Schema(description = "실물 이미지 url", example = "http://")
    private String idImageUrl;

    @Schema(description = "실명", example = "김다은")
    private String realName;

    @Schema(description = "학적상태", example = "휴학")
    private AcademicStatus academicStatus;

    @Schema(description = "입학년월", example = "2019/03")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth entranceDate;

    @Schema(description = "졸업년월", example = "2023/08")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth graduationDate;

    @Schema(description = "졸업여부", example = "false")
    private Boolean isGraduation;

    @Schema(description = "주전공명", example = "IT공학전공")
    private Major mainMajor;

    @Schema(description = "복수전공명", example = "빅데이터분석학전공")
    private Major doubleMajor;

    @Schema(description = "부전공명", example = "미디어학부")
    private Major minorMajor;
}
