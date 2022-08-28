package com.project.sooktoring.profile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "멘토 프로필 조회 시 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorProfileResponse {

    @Schema(description = "멘토 프로필 id", example = "1")
    private Long profileId;

    @Schema(description = "프로필 이미지 url", example = "http://")
    private String profileImageUrl;

    @Schema(description = "현재 직무", example = "백엔드 개발자")
    private String job;

    @Schema(description = "직무 연차", example = "3")
    private Long workYear;

    @Schema(description = "닉네임", example = "개발자국")
    private String nickName;

    @Schema(description = "멘토 여부", example = "true")
    private Boolean isMentor;

    @Schema(description = "학사 이후 학력 리스트")
    private List<MasterDoctorResponse> masterDoctorList;

    @Schema(description = "경력 리스트")
    private List<CareerResponse> careerList;

    public MentorProfileResponse(Long profileId, String profileImageUrl, String job, Long workYear, String nickName, Boolean isMentor) {
        this.profileId = profileId;
        this.profileImageUrl = profileImageUrl;
        this.job = job;
        this.workYear = workYear;
        this.nickName = nickName;
        this.isMentor = isMentor;
    }

    public void changeList(List<MasterDoctorResponse> masterDoctorList, List<CareerResponse> careerList) {
        this.masterDoctorList = masterDoctorList;
        this.careerList = careerList;
    }
}
