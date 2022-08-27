package com.project.sooktoring.profile.service;

import com.project.sooktoring.auth.domain.User;
import com.project.sooktoring.auth.enumerate.Role;
import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.utils.AcademicInfoUtil;
import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.common.utils.S3Uploader;
import com.project.sooktoring.common.utils.UserUtil;
import com.project.sooktoring.profile.domain.AcademicInfo;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.profile.dto.request.AcademicInfoRequest;
import com.project.sooktoring.profile.dto.response.AcademicInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.project.sooktoring.common.exception.ErrorCode.*;
import static com.project.sooktoring.profile.enumerate.AcademicStatus.*;

@RequiredArgsConstructor
@Service
public class AcademicInfoService {

    private final UserUtil userUtil;
    private final AcademicInfoUtil academicInfoUtil;
    private final ProfileUtil profileUtil;
    private final S3Uploader s3Uploader;

    public AcademicInfoResponse getMyAcademicInfo() {
        AcademicInfo academicInfo = academicInfoUtil.getCurrentAcademicInfo();
        return AcademicInfoResponse.builder()
                .academicInfoId(academicInfo.getId())
                .idImageUrl(academicInfo.getImageUrl())
                .realName(academicInfo.getRealName())
                .academicStatus(academicInfo.getAcademicStatus())
                .entranceDate(academicInfo.getEntranceDate())
                .graduationDate(academicInfo.getGraduationDate())
                .isGraduation(academicInfo.getIsGraduation())
                .mainMajor(academicInfo.getMainMajor())
                .doubleMajor(academicInfo.getDoubleMajor())
                .minorMajor(academicInfo.getMinorMajor())
                .build();
    }

    @Transactional
    public void update(AcademicInfoRequest academicInfoRequest, MultipartFile file) {
        //학적상태에 따라 멘토 & 멘티 여부 설정
        User user = userUtil.getCurrentUser();
        Profile profile = profileUtil.getCurrentProfile();
        Boolean isMentor = (academicInfoRequest.getAcademicStatus() != ATTEND && academicInfoRequest.getAcademicStatus() != BREAK);
        //멘토 -> 멘티 불가능
        if (user.getRole() == Role.ROLE_MENTOR && !isMentor) {
            throw new CustomException(INVALID_ACADEMIC_STATUS);
        }
        user.changeRole(isMentor);
        profile.changeIsMentor(isMentor);

        AcademicInfo academicInfo = academicInfoUtil.getCurrentAcademicInfo();
        academicInfo.update(academicInfoRequest);
        String imageUrl = s3Uploader.getImageUrl(file, academicInfo.getImageUrl());
        academicInfo.changeImageUrl(imageUrl);
    }
}
