package com.project.sooktoring.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK
    }

    private String roomId;
    private MessageType type;
    private String sender;
    private String message;

}
