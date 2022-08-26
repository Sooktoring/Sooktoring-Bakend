package com.project.sooktoring.profile.dto.response;

import com.project.sooktoring.profile.enumerate.AcademicStatus;
import com.project.sooktoring.profile.enumerate.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.YearMonth;

@Builder
@AllArgsConstructor
public class AcademicInfoResponse {

    private Long academicInfoId;

    private String idImageUrl;

    private String realName;

    private AcademicStatus academicStatus;

    private YearMonth entranceDate;

    private YearMonth graduationDate;

    private Boolean isGraduation;

    private Major mainMajor;

    private Major doubleMajor;

    private Major minorMajor;
}
