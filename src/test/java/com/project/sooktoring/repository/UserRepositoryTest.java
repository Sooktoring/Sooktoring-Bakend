package com.project.sooktoring.repository;

import com.project.sooktoring.auth.enumerate.AuthProvider;
import com.project.sooktoring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    public void querydslTest() {
        User user1 = User.builder()
                .email("abcd1234@sookmyung.ac.kr")
                .name("김가가")
                .provider(AuthProvider.GOOGLE)
                .providerId("1234")
                .imageUrl("http://")
                .build();

        User user2 = User.builder()
                .email("abcd5678@sookmyung.ac.kr")
                .name("김나나")
                .provider(AuthProvider.GOOGLE)
                .providerId("5678")
                .imageUrl("http://")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> result = userRepository.findAllByQuerydsl();
        assertThat(result).extracting("name").containsExactly("김가가", "김나나");
    }
}