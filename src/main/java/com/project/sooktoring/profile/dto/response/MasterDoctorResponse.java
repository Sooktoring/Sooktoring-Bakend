package com.project.sooktoring.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.sooktoring.profile.enumerate.Degree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "학사 이후 학력 조회 시 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MasterDoctorResponse {

    @Schema(description = "학사 이후 학력 id", example = "1")
    private Long masterDoctorId;

    @Schema(description = "학력", example = "석사")
    private Degree degree;

    @Schema(description = "입학년월", example = "2019/03")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth entranceDate;

    @Schema(description = "졸업년월", example = "2023/02")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth graduationDate;

    @Schema(description = "재학중 여부", example = "true")
    private Boolean isAttend;

    @Schema(description = "대학명", example = "숙명여자대학교")
    private String univName;

    @Schema(description = "주전공명", example = "빅데이터분석학전공")
    private String mainMajor;
}
