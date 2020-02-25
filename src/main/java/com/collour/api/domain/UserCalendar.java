package com.collour.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_calendar")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_calendar_no")
    private Long no;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "calendar_no")
    private Calendar calendar;

    public UserCalendar(User user, Calendar calendar){
        this.user = user;
        this.calendar = calendar;
    }
}
