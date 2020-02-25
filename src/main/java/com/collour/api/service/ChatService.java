package com.collour.api.service;

import com.collour.api.dto.ChatMessageDTO;
import com.collour.api.dto.ChatRoomDTO;

import java.util.List;

public interface ChatService {

    List<ChatRoomDTO> getChatRoomList(String username) throws Exception;

    List<ChatMessageDTO> getChatRoomMessageList(String username, Long chatRoomNo) throws Exception;

    ChatRoomDTO createChatRoom(String chatRoomName) throws Exception;

    void sendChatMessage(ChatMessageDTO chatMessageDTO);

}
