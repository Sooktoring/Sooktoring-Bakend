package com.project.sooktoring.auth.domain;

import com.project.sooktoring.auth.enumerate.AuthProvider;
import com.project.sooktoring.common.BaseTimeEntity;
import com.project.sooktoring.enumerate.Role;
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_MENTEE;

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

    //UserProfile로 이동!!!!!!!!!!!!
    public void changeRole(Boolean isMentor) {
        if (role == Role.ROLE_MENTEE && isMentor) {
           role = Role.ROLE_MENTOR;
        } else if (role == Role.ROLE_MENTOR && !isMentor) {
            role = Role.ROLE_MENTEE;
        }
    }
}
