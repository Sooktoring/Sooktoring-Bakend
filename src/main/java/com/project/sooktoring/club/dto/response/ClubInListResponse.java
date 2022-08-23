package com.project.sooktoring.club.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubInListResponse {

    private Long clubId;

    private String logoUrl;

    private String name;

    private String desc;

    private String recruitField;
}
