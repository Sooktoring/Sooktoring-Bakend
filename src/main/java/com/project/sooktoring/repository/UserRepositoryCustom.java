package com.project.sooktoring.repository;

import com.project.sooktoring.domain.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findAllByQuerydsl();
}
