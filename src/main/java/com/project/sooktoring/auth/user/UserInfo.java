package com.project.sooktoring.auth.user;

import com.project.sooktoring.auth.domain.User;

public interface UserInfo {

    User getUser(String idToken);
}
