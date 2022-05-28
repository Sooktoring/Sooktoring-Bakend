package com.project.sooktoring.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sooktoring.domain.DoubleMajor;
import com.project.sooktoring.domain.MainMajor;
import com.project.sooktoring.domain.Minor;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserProfileRequest {

    @NotNull
    private MainMajor mainMajor;
    private DoubleMajor doubleMajor;
    private Minor minor;

    @NotNull
    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth entranceDate; //년월만 다룸
    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth gradDate;

    private String job;
    private Long workYear;

    @NotNull
    private Boolean isMentor;

    @JsonProperty("activities")
    private List<ActivityRequest> activityRequests;
    @JsonProperty("careers")
    private List<CareerRequest> careerRequests;
}
