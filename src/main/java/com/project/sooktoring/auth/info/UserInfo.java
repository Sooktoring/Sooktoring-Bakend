package com.project.sooktoring.auth.info;

import com.project.sooktoring.auth.domain.User;

public interface UserInfo {

    User getUser(String idToken);
}
