package com.collour.api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notice")
@Getter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_no")
    private Long no;

    @Column(nullable = false, length = 500)
    @Setter
    private String title;

    @Column(nullable = false, length = 10000)
    @Setter
    private String content;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no")
    @Setter
    private User author;

    @Temporal(value = TemporalType.DATE)
    @Setter
    private Date date;

    @Temporal(value = TemporalType.TIME)
    @Setter
    private Date time;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    @Setter
    private Long hits = 0L;

    @Column(nullable = false, columnDefinition = "int default 1")
    @Setter
    private Integer valid = 1;
}
