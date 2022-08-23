package com.project.sooktoring.club.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubOutListResponse {

    private Long clubId;

    private String logoUrl;

    private String name;

    private String desc;

    private String recruitField;
}
