package com.project.sooktoring.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.YearMonth;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("대외활동 요청 정보")
public class ActivityRequest {

    @JsonProperty("activityId")
    @ApiModelProperty(value = "대외활동 id", example = "1")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "대외활동명", example = "", required = true)
    private String title;

    @NotNull
    @ApiModelProperty(value = "대외활동 설명", example = "", required = true)
    private String details;

    @NotNull
    @JsonFormat(pattern = "yyyy/MM")
    @ApiModelProperty(value = "대외활동 시작일", example = "2022/05", required = true)
    private YearMonth startDate;

    @JsonFormat(pattern = "yyyy/MM")
    @ApiModelProperty(value = "대외활동 종료일", example = "2022/07")
    private YearMonth endDate;
}
