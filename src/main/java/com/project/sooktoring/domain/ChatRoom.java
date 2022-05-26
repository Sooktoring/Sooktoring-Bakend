package com.project.sooktoring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatRoom {

    @Id
    @Column(name = "chat_room_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private Mentoring mentoring; //FK이면서 PK

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Column(name = "chat_created_date")
    private LocalDateTime createdDate;

    public static ChatRoom create(Mentoring mentoring) {
        return ChatRoom.builder()
                .mentoring(mentoring)
                .createdDate(LocalDateTime.now())
                .build();
    }
}
