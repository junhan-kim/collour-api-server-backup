package com.collour.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CalendarDTO {

    private Long no;
    private String title;
    private String authorName;
    private Long teamNo;
    private List<String> relatedUsernameList;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
