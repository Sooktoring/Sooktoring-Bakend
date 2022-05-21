package com.project.sooktoring.repository;

import com.project.sooktoring.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.sooktoring.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findAllByQuerydsl() {
        return queryFactory
                .selectFrom(user)
                .fetch();
    }
}
