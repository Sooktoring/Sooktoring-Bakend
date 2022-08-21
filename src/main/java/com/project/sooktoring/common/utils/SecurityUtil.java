package com.project.sooktoring.common.utils;

import com.project.sooktoring.user.info.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        return ((UserPrincipal) authentication.getPrincipal()).getUserId();
    }
}
