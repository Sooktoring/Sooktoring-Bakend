package com.project.sooktoring.domain;

import com.project.sooktoring.auth.enumerate.AuthProvider;
import com.project.sooktoring.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    /*
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_MENTEE;
     */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    //구글에서 이메일 외에 사용자 별로 부여하는 고유한 번호?
    @Column(nullable = false)
    private String providerId;

    public void updateUser(User user) {
        this.providerId = user.getProviderId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
    }
}
