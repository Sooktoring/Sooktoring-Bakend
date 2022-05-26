package com.project.sooktoring.dto.request;

import com.project.sooktoring.domain.MentoringCat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MtrRequest {

    @NotNull
    private Long mentorId;

    @NotNull
    private MentoringCat cat;

    @NotNull
    private String reason;

    @NotNull
    private String talk;
}
