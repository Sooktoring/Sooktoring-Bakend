package com.project.sooktoring.user.auth.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "token_key")
    private Long key;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "token_key")
    private User loginUser; //FK이면서 PK

    @Column(name = "token_value")
    private String value;

    public void updateToken(String value) {
        this.value = value;
    }

    //생성/수정 시간 컬럼 추가하여 배치 작업으로 만료된 토큰 DB에서 삭제
}
