package com.project.sooktoring.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "멘토 프로필 수정 시 정보 전달하는 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorProfileRequest {

    private String job;

    private String nickName;

    private List<MasterDoctorRequest> masterDoctorList;

    private List<CareerRequest> careerList;
}
