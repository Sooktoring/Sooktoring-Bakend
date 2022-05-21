package com.project.sooktoring.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponse {

    private String sub; //구글 내 아이디
    private String email;
    private String name;
    private String picture;
}
