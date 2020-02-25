package com.collour.api.controller;

import com.collour.api.dto.ChatMessageDTO;
import com.collour.api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO message) {
        chatService.sendChatMessage(message);
    }
}
