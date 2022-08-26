package com.project.sooktoring.profile.dto.request;

import com.project.sooktoring.profile.enumerate.AcademicStatus;
import com.project.sooktoring.profile.enumerate.Major;
import lombok.Getter;

import java.time.YearMonth;

@Getter
public class AcademicInfoRequest {

    private String realName;

    private AcademicStatus academicStatus;

    private YearMonth entranceDate;

    private YearMonth graduationDate;

    private Boolean isGraduation;

    private Major mainMajor;

    private Major doubleMajor;

    private Major minorMajor;
}
