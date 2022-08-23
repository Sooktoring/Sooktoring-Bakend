package com.project.sooktoring.club.dto.request;

import com.project.sooktoring.club.enumerate.ClubKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubRequest {

    private ClubKind kind;

    private String name;

    private String desc;

    private String recruitField;

    private String recruitTime;

    private Boolean isRecruit;

    private List<ClubUrlRequest> clubUrlList;
}
