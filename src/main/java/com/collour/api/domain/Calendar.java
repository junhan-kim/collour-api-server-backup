package com.collour.api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "calendar")
@Getter
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_no")
    private Long no;

    @Column(nullable = false, length = 500)
    @Setter
    private String title;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    @Setter
    private User author;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_no")
    @Setter
    private Team team;

    @OneToMany(mappedBy = "calendar")
    private List<UserCalendar> relatedUserList = new ArrayList<>();

    @Column(length = 10000)
    @Setter
    private String content;

    @Column(nullable = false)
    @Setter
    private LocalDateTime startDate;

    @Column(nullable = false)
    @Setter
    private LocalDateTime endDate;
}
