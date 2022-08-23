package com.project.sooktoring.club.dto.response;

import com.project.sooktoring.club.domain.Club;
import com.project.sooktoring.club.enumerate.ClubKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
public class ClubResponse {

    private Long clubId;

    private String logoUrl;

    private ClubKind kind;

    private String name;

    private String desc;

    private String recruitField;

    private String recruitTime;

    private Boolean isRecruit;

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
