package com.collour.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatMessageDTO {

    public enum MessageType {
        SYSTEM, USER
    }

    private MessageType type;
    private String chatRoomId;
    private String sender;

    @Setter
    private String message;
}
