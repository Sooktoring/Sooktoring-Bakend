package com.project.sooktoring.club.dto.response;

import lombok.Getter;

@Getter
public class ClubRecruitListResponse {

    private Long clubId;

    private String logoUrl;

    private String name;

    private String desc;

    private String recruitField;

    private String recruitUrl;

    public ClubRecruitListResponse(Long clubId, String logoUrl, String name, String desc, String recruitField) {
        this.clubId = clubId;
        this.logoUrl = logoUrl;
        this.name = name;
        this.desc = desc;
        this.recruitField = recruitField;
    }

    public void changeRecruitUrl(String recruitUrl) {
        this.recruitUrl = recruitUrl;
    }
}
