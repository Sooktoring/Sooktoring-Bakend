package com.project.sooktoring.auth.client;

import com.project.sooktoring.domain.User;

public interface UserInfo {

    User getUser(String accessToken);
}
