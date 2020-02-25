package com.collour.api.controller;

import com.collour.api.common.JwtTokenUtil;
import com.collour.api.dto.ChatMessageDTO;
import com.collour.api.dto.ChatRoomDTO;
import com.collour.api.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/chats")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/chatrooms")
    public ResponseEntity<List<ChatRoomDTO>> getChatRoomList(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok(chatService.getChatRoomList(jwtTokenUtil.getUsernameFromToken(token)));
    }

    /*
        @GetMapping(value = "/chatrooms/{chatroom}/users")
        public ResponseEntity<List<ChatRoomDTO>> getChatRoomUserList(@RequestHeader("Authorization") String token, @PathVariable String chatroom) throws Exception {
            return ResponseEntity.ok(chatService.getChatRoomUserList(jwtTokenUtil.getUsernameFromToken(token), chatroom));
        }
    */
    @GetMapping(value = "/chatrooms/{chatroom}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getChatRoomMessageList(@RequestHeader("Authorization") String token, @PathVariable String chatroom) throws Exception {
        return ResponseEntity.ok(chatService.getChatRoomMessageList(jwtTokenUtil.getUsernameFromToken(token), Long.getLong(chatroom)));
    }
}
