package com.project.sooktoring.club.dto.response;

import com.project.sooktoring.club.domain.Club;
import com.project.sooktoring.club.enumerate.ClubKind;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "동아리 상세 조회 시 반환하는 DTO")
@Getter @Builder
@AllArgsConstructor
public class ClubResponse {

    @Schema(description = "동아리 id", example = "1")
    private Long clubId;

    @Schema(description = "동아리 로고 url", example = "http://")
    private String logoUrl;

    @Schema(description = "동아리 종류", example = "교내")
    private ClubKind kind;

    @Schema(description = "동아리명", example = "SOLUX")
    private String name;

    @Schema(description = "동아리 소개", example = "숙명 중앙 개발 동아리")
    private String desc;

    @Schema(description = "모집분야", example = "백엔드")
    private String recruitField;

    @Schema(description = "모집시기", example = "매년3월초")
    private String recruitTime;

    @Schema(description = "모집중 여부", example = "false")
    private Boolean isRecruit;

    @Schema(description = "동아리 URL 리스트")
    private List<ClubUrlResponse> clubUrlList;

    public static ClubResponse create(Club club, List<ClubUrlResponse> clubUrlList) {
        return ClubResponse.builder()
                .clubId(club.getId())
                .logoUrl(club.getLogoUrl())
                .kind(club.getKind())
                .name(club.getName())
                .desc(club.getDesc())
                .recruitField(club.getRecruitField())
                .recruitTime(club.getRecruitTime())
                .isRecruit(club.getIsRecruit())
                .clubUrlList(clubUrlList)
                .build();
    }
}
