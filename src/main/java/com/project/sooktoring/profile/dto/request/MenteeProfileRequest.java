package com.project.sooktoring.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "멘티 프로필 수정 시 정보 전달하는 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenteeProfileRequest {

    @Schema(description = "원하는 직무", example = "백엔드 개발자", required = true)
    private String job;

    @Schema(description = "닉네임 (중복여부 체크 필수)", example = "단이", required = true)
    private String nickName;

    @Schema(description = "대외활동 리스트", required = true)
    private List<ActivityRequest> activityList;
}
