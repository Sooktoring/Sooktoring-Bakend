package com.project.sooktoring.dto.request;

import com.project.sooktoring.enumerate.MentoringCat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MtrUpdateRequest {

    @NotNull
    private MentoringCat cat;

    @NotNull
    private String reason;

    @NotNull
    private String talk;
}
