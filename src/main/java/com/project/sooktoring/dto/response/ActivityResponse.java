package com.project.sooktoring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {

    @JsonProperty("activityId")
    private Long id;

    private String details;

    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth startDate;

    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth endDate;
}
