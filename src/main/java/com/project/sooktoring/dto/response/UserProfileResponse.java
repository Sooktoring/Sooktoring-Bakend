package com.project.sooktoring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sooktoring.domain.DoubleMajor;
import com.project.sooktoring.domain.MainMajor;
import com.project.sooktoring.domain.Minor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserProfileResponse {

    @JsonProperty("userId")
    private Long id;

    private MainMajor mainMajor;
    private DoubleMajor doubleMajor;
    private Minor minor;

    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth entranceDate; //년월만 다룸
    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth gradDate;

    private String job;
    private Long workYear;

    private Boolean isMentor;

    @JsonProperty("activities")
    private List<ActivityResponse> activityResponses;
    @JsonProperty("careers")
    private List<CareerResponse> careerResponses;

    public UserProfileResponse(Long id, MainMajor mainMajor, DoubleMajor doubleMajor, Minor minor,
                               YearMonth entranceDate, YearMonth gradDate, String job, Long workYear, Boolean isMentor) {
        this.id = id;
        this.mainMajor = mainMajor;
        this.doubleMajor = doubleMajor;
        this.minor = minor;
        this.entranceDate = entranceDate;
        this.gradDate = gradDate;
        this.job = job;
        this.workYear = workYear;
        this.isMentor = isMentor;
    }

    public UserProfileResponse changeList(List<ActivityResponse> activityResponses, List<CareerResponse> careerResponses) {
        this.activityResponses = activityResponses;
        this.careerResponses = careerResponses;
        return this;
    }
}
