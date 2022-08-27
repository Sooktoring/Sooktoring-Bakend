package com.project.sooktoring.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.sooktoring.profile.enumerate.Degree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MasterDoctorResponse {

    private Long masterDoctorId;

    private Degree degree;

    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth entranceDate;

    @JsonFormat(pattern = "yyyy/MM", shape = STRING)
    private YearMonth graduationDate;

    private Boolean isAttend;

    private String univName;

    private String mainMajor;
}
