package com.project.sooktoring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "직무경력 조회 시 반환하는 DTO")
public class CareerResponse {

    @Schema(description = "직무경력 id", example = "1")
    @JsonProperty("careerId")
    private Long id;

    @Schema(description = "직무명", example = "테스트 서버 벡엔드")
    private String job;

    @Schema(description = "회사명", example = "네이버")
    private String company;

    @Schema(description = "직무 시작일", example = "2022/05")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth startDate;

    @Schema(description = "직무 종료일", example = "2022/07")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth endDate;
}
