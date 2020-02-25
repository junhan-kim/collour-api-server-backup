package com.collour.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long no;


    // required

    @Column(nullable = false, length = 20)
    @Setter
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    @Setter
    private String password;

    @Column(nullable = false, length = 100)
    @Setter
    private String email;


    // optional

    @Column(length = 100)
    @Setter
    private String name;

    @Setter
    private LocalDate birth;

    @Column(length = 100)
    @Setter
    private String major;

    @Setter
    private String photoUri;  // photo's relative path


    // system

    @OneToMany(mappedBy = "author")
    private List<Calendar> calendarList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserCalendar> relatedCalendarList = new ArrayList<>();


    // Revise ManyToMany --> OneToMany for Test **************************
/*
    @ManyToMany
    @JoinTable(name = "user_team",
                joinColumns = @JoinColumn(name = "user_no"),
                inverseJoinColumns = @JoinColumn(name = "team_no"))
    private List<Team> teamList = new ArrayList<>();
*/
    @OneToMany(mappedBy = "user")
    private List<UserTeam> teamList = new ArrayList<>();

    // Revise ManyToMany --> OneToMany for Test **************************


    @ManyToMany
    @JoinTable(name = "user_chat_room",
            joinColumns = @JoinColumn(name = "user_no"),
            inverseJoinColumns = @JoinColumn(name = "chat_room_no"))
    private List<ChatRoom> chatRoomList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessageList = new ArrayList<>();


    @Setter
    private Boolean admin = false;

    @Setter
    private Boolean valid = true;

    @Setter
    private Boolean emailValid = false;
}
