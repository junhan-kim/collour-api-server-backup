package com.collour.api.dto;

import com.collour.api.domain.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    ChatMessage toChatMessage(ChatMessageDTO chatMessageDTO);

    ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage);

}
