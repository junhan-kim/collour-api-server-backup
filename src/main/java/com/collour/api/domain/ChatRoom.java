
package com.collour.api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "chat_room")
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_no")
    private Long no;

    @Column
    @Setter
    private String name;

    //@ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
    //@JoinColumn(name = "group_no")
    //@Setter
    //private Team group;

    @ManyToMany(mappedBy = "chatRoomList")
    private List<User> userList = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessageList = new LinkedList<>();
}


