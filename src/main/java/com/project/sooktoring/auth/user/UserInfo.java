package com.project.sooktoring.auth.user;

import com.project.sooktoring.domain.User;

public interface UserInfo {

    User getUser(String idToken);
}
