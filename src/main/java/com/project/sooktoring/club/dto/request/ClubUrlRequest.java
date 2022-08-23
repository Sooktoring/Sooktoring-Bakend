package com.project.sooktoring.club.dto.request;

import com.project.sooktoring.club.enumerate.ClubUrlCat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubUrlRequest {

    private Long clubUrlId;

    private ClubUrlCat cat;

    private String url;
}
