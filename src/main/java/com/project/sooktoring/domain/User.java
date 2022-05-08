package com.project.sooktoring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sooktoring.enumerate.AuthProvider;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

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
    private Role role;
     */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    //구글에서 이메일 외에 사용자 별로 부여하는 고유한 번호?
    @Column(nullable = false)
    private String providerId;
}
