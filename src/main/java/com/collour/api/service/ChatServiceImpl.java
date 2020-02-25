package com.collour.api.service;

import com.collour.api.domain.ChatMessage;
import com.collour.api.domain.ChatRoom;
import com.collour.api.domain.User;
import com.collour.api.dto.ChatMessageDTO;
import com.collour.api.dto.ChatRoomDTO;
import com.collour.api.dto.ExceptionDTO;
import com.collour.api.repository.ChatMessageRepository;
import com.collour.api.repository.ChatRoomRepository;
import com.collour.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessageSendingOperations sendingOperations;

    @Override
    public List<ChatRoomDTO> getChatRoomList(String username) throws Exception {
        User user = userRepository.findByUsernameAndValid(username, true);
        if (user == null) throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_NOT_FOUND);

        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomRepository.findByUserListContainsOrderByNoDesc(user)) {
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();

            chatRoomDTO.setNo(chatRoom.getNo());
            chatRoomDTO.setName(chatRoom.getName());

            chatRoomDTOList.add(chatRoomDTO);
        }

        return chatRoomDTOList;
    }

    @Override
    public List<ChatMessageDTO> getChatRoomMessageList(String username, Long chatRoomNo) throws Exception {
        User user = userRepository.findByUsernameAndValid(username, true);
        if (user == null) throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_NOT_FOUND);

        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatRoomNo);
        if (!chatRoom.isPresent()) throw new ExceptionDTO(ExceptionDTO.ErrorCode.CHATROOM_NOT_FOUND);

        List<ChatMessageDTO> chatMessageDTOList = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessageRepository.findByChatRoomOrderByNoDesc(chatRoom.get())) {
            ChatMessageDTO chatMessageDTO = new ChatMessageDTO();

            chatMessageDTO.setMessage(chatMessage.getPayload());

            chatMessageDTOList.add(chatMessageDTO);
        }

        return chatMessageDTOList;
    }

    @Override
    public ChatRoomDTO createChatRoom(String name) throws Exception {
        return null;
    }

    @Override
    public void sendChatMessage(ChatMessageDTO chatMessageDTO) {
        if (ChatMessageDTO.MessageType.SYSTEM.equals(chatMessageDTO.getType()))
            chatMessageDTO.setMessage("[알림] " + chatMessageDTO.getMessage());

        sendingOperations.convertAndSend("/sub/chat/" + chatMessageDTO.getChatRoomId(), chatMessageDTO);
    }
}
