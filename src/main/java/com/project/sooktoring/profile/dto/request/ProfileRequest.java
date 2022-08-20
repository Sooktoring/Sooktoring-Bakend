package com.project.sooktoring.profile.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sooktoring.profile.domain.DoubleMajor;
import com.project.sooktoring.profile.domain.MainMajor;
import com.project.sooktoring.profile.domain.Minor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "유저 프로필 수정 시 정보 전달하는 DTO")
public class ProfileRequest {

    @Schema(required = true, description = "현재 이용자 실명")
    @NotNull
    private String realName;

    @Schema(required = true, description = "주전공")
    @NotNull
    private MainMajor mainMajor;

    @Schema(description = "복수전공")
    private DoubleMajor doubleMajor;

    @Schema(description = "부전공")
    private Minor minor;

    @Schema(description = "입학년월", example = "2019/03", required = true)
    @NotNull
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth entranceDate; //년월만 다룸

    @Schema(description = "졸업년월", example = "2023/02")
    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth gradDate;

    @Schema(description = "현재 직업", example = "백엔드 개발자")
    private String job;

    @Schema(description = "현재 직업 연차", example = "1")
    private Long workYear;

    @Schema(description = "멘토 여부", example = "true", required = true)
    @NotNull
    private Boolean isMentor;

    @Schema(description = "프로필 이미지 url")
    private String imageUrl;

    @Schema(description = "대외활동 내역 리스트")
    @JsonProperty("activities")
    private List<ActivityRequest> activityRequests;

    @Schema(description = "직무경력 내역 리스트")
    @JsonProperty("careers")
    private List<CareerRequest> careerRequests;

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
