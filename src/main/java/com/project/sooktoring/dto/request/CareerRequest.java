package com.project.sooktoring.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.YearMonth;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "직무경력 등록, 수정 시 정보 전달하는 DTO")
public class CareerRequest {

    @Schema(description = "직무경력 id", example = "1") //수정 시에는 required = true
    @JsonProperty("careerId")
    private Long id;

    @Schema(description = "직무명", example = "테스트 서버 벡엔드", required = true)
    @NotNull
    private String job;

    @Schema(description = "회사명", example = "네이버", required = true)
    @NotNull
    private String company;

    @Schema(description = "직무 시작일", example = "2022/05", required = true)
    @NotNull
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth startDate;

    @Schema(description = "직무 종료일", example = "2022/07")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth endDate;
}
