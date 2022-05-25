package com.project.sooktoring.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.YearMonth;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CareerRequest {

    @JsonProperty("careerId")
    private Long id;

    @NotNull
    private String details;

    @NotNull
    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth startDate;

    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth endDate;
}
