package com.project.sooktoring.auth.jwt;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "token_key")
    private String key; //providerId 값

    @Column(name = "token_value")
    private String value;

    public void updateToken(String value) {
        this.value = value;
    }

    //생성/수정 시간 컬럼 추가하여 배치 작업으로 만료된 토큰 삭제
}
