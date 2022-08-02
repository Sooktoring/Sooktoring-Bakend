package com.project.sooktoring.user.auth.info;

import com.project.sooktoring.user.auth.domain.User;

public interface UserInfo {

    User getUser(String idToken);
}
