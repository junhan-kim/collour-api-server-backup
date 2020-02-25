package com.collour.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_no")
    private Long no;

    @Column(length = 100)
    @Setter
    private String teamname;

    @Setter
    private String photo;

    @OneToMany(mappedBy = "team")
    private List<UserTeam> userList = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Calendar> calendarList = new ArrayList<>();

//    @OneToMany(mappedBy = "user_team")
//    private List<ChatRoom> chatRoomList = new ArrayList<>();

}
