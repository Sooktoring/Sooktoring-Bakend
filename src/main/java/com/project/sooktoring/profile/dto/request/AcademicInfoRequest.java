package com.project.sooktoring.profile.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.sooktoring.profile.enumerate.AcademicStatus;
import com.project.sooktoring.profile.enumerate.Major;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.YearMonth;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "학적정보 수정 시 정보 전달하는 DTO")
@Getter
public class AcademicInfoRequest {

    @Schema(description = "실명", example = "김다은", required = true)
    private String realName;

    @Schema(description = "학적상태", example = "재학", required = true)
    private AcademicStatus academicStatus;

    @Schema(description = "입학년월", example = "2019/03", required = true)
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth entranceDate;

    @Schema(description = "졸업년월", example = "2023/08")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth graduationDate;

    @Schema(description = "졸업여부", example = "false", required = true)
    private Boolean isGraduation;

    @Schema(description = "주전공명", example = "IT공학전공", required = true)
    private Major mainMajor;

    @Schema(description = "복수전공명", example = "빅데이터분석학전공")
    private Major doubleMajor;

    @Schema(description = "부전공명", example = "미디어학부")
    private Major minorMajor;
}
