package com.project.sooktoring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sooktoring.domain.DoubleMajor;
import com.project.sooktoring.domain.MainMajor;
import com.project.sooktoring.domain.Minor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@NoArgsConstructor
@Schema(description = "유저 프로필 조회 시 반환하는 DTO")
public class UserProfileResponse {

    @Schema(description = "이용자 id", example = "1")
    @JsonProperty("userId")
    private Long id;

    @Schema(required = true, description = "이용자 실명")
    private String realName;

    @Schema(description = "주전공")
    private MainMajor mainMajor;

    @Schema(description = "복수전공")
    private DoubleMajor doubleMajor;

    @Schema(description = "부전공")
    private Minor minor;

    @Schema(description = "입학년월", example = "2019/03")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth entranceDate; //년월만 다룸

    @Schema(description = "졸업년월", example = "2023/02")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth gradDate;

    @Schema(description = "현재 직업", example = "백엔드 개발자")
    private String job;

    @Schema(description = "현재 직업 연차", example = "1")
    private Long workYear;

    @Schema(description = "멘토 여부", example = "true")
    private Boolean isMentor;

    @Schema(description = "프로필 이미지 url")
    private String imageUrl;

    @Schema(description = "대외활동 내역 리스트")
    @JsonProperty("activities")
    private List<ActivityResponse> activityResponses;

    @Schema(description = "직무경력 내역 리스트")
    @JsonProperty("careers")
    private List<CareerResponse> careerResponses;

    public UserProfileResponse(Long id, String realName, MainMajor mainMajor, DoubleMajor doubleMajor, Minor minor,
                               YearMonth entranceDate, YearMonth gradDate, String job, Long workYear, Boolean isMentor, String imageUrl) {
        this.id = id;
        this.realName = realName;
        this.mainMajor = mainMajor;
        this.doubleMajor = doubleMajor;
        this.minor = minor;
        this.entranceDate = entranceDate;
        this.gradDate = gradDate;
        this.job = job;
        this.workYear = workYear;
        this.isMentor = isMentor;
        this.imageUrl = imageUrl;
    }

    public UserProfileResponse changeList(List<ActivityResponse> activityResponses, List<CareerResponse> careerResponses) {
        this.activityResponses = activityResponses;
        this.careerResponses = careerResponses;
        return this;
    }
}
