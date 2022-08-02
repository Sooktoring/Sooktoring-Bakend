package com.project.sooktoring.domain;

import com.project.sooktoring.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MentoringCard extends BaseTimeEntity {

    @Id
    @Column(name = "mtr_card_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "mtr_card_id")
    private Mentoring mentoring; //FK이면서 PK

    @Column(name = "mtr_card_title", nullable = false, length = 100)
    private String title;

    @Column(name = "mtr_card_content", nullable = false, length = 500)
    private String content;

    public void updateCard(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
