package com.project.sooktoring.club.dto.response;

import com.project.sooktoring.club.enumerate.ClubKind;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClubResponse {

    private Long id;

    private String logoUrl;

    private ClubKind kind;

    private String name;

    private String desc;

    private String recruitField;

    private String recruitTime;

    private Boolean isRecruit;

    private List<ClubUrlResponse> clubUrlList;
}
