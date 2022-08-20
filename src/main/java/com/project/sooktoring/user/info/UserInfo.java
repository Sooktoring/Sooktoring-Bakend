package com.project.sooktoring.user.info;

import com.project.sooktoring.user.domain.User;

public interface UserInfo {

    User getUser(String idToken);
}
