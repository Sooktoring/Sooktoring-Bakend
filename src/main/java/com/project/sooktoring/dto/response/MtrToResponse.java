package com.project.sooktoring.dto.response;

import com.project.sooktoring.domain.MentoringCat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MtrToResponse {

    protected Long mtrId;

    private Long menteeId;
    private String menteeName;
    private String menteeMainMajor;

    protected MentoringCat cat;

    protected String reason;

    protected String talk;

    protected Boolean isAccept;
}
