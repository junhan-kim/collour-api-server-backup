package com.collour.api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
@Getter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_no")
    private Long no;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    @Setter
    private User user;

    @ManyToOne(targetEntity = ChatRoom.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_no")
    @Setter
    private ChatRoom chatRoom;

    @Setter
    private String payload;

    @Temporal(value = TemporalType.DATE)
    @Setter
    private Date date;

    @Temporal(value = TemporalType.TIME)
    @Setter
    private Date time;

}
