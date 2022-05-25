package com.project.sooktoring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserProfileResponse {

    @JsonProperty("userId")
    private Long id;

    private String mainMajor;
    private String doubleMajor;
    private String minor;

    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth entranceDate; //년월만 다룸
    @JsonFormat(pattern = "yyyy/MM")
    private YearMonth gradDate;

    private String job;
    private Boolean isMentor;

    @JsonProperty("activities")
    private List<ActivityResponse> activityResponses;
    @JsonProperty("careers")
    private List<CareerResponse> careerResponses;

    public UserProfileResponse(Long id, String mainMajor, String doubleMajor, String minor, YearMonth entranceDate, YearMonth gradDate, String job, Boolean isMentor) {
        this.id = id;
        this.mainMajor = mainMajor;
        this.doubleMajor = doubleMajor;
        this.minor = minor;
        this.entranceDate = entranceDate;
        this.gradDate = gradDate;
        this.job = job;
        this.isMentor = isMentor;
    }

    public UserProfileResponse changeList(List<ActivityResponse> activityResponses, List<CareerResponse> careerResponses) {
        this.activityResponses = activityResponses;
        this.careerResponses = careerResponses;
        return this;
    }
}
