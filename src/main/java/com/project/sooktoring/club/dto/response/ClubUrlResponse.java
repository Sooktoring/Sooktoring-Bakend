package com.project.sooktoring.club.dto.response;

import com.project.sooktoring.club.enumerate.ClubUrlCat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubUrlResponse {

    private ClubUrlCat cat;

    private String url;
}
