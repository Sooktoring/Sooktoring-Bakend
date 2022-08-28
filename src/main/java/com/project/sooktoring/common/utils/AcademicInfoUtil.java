package com.project.sooktoring.common.utils;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.profile.domain.AcademicInfo;
import com.project.sooktoring.profile.repository.AcademicInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Component
public class AcademicInfoUtil {

    private final S3Uploader s3Uploader;
    private final AcademicInfoRepository academicInfoRepository;

    public AcademicInfo getCurrentAcademicInfo() {
        return academicInfoRepository.findByUserId(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_ACADEMIC_INFO));
    }

    public void withdraw(AcademicInfo academicInfo) {
        //실물 이미지 삭제
        if (StringUtils.hasText(academicInfo.getImageUrl())) {
            s3Uploader.deleteImg(academicInfo.getImageUrl());
        }
        academicInfoRepository.delete(academicInfo);
    }
}
