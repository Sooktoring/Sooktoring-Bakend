package com.project.sooktoring.dto.response;

import com.project.sooktoring.enumerate.MentoringCat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MtrFromResponse {

    protected Long mtrId;

    private Long mentorId;
    private String mentorName;
    private String mentorMainMajor;
    private String job;

    protected MentoringCat cat;

    protected String reason;

    protected String talk;

    protected Boolean isAccept;
}
