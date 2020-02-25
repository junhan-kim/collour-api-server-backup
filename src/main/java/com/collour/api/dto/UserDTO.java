package com.collour.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    @Size(min = 6, max = 15)
    @Pattern(regexp = "(?=^[^0-9])[a-z0-9]*")   // 1. can use a-z,0-9   2. not start with 0-9
    private String username;

    @Size(min = 9, max = 16)
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()])[A-Za-z0-9!@#$%^&*()]*")
    // 1. can use A-Z,a-z,0-9,special characters  2. at least one char, one num, one sc
    private String password;

    @Size(min = 3, max = 40)
    @Email
    private String email;


    @Size(max = 100)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private LocalDate birth;

    @Size(max = 100)
    private String major;

    private String photoUri;

    private List<CalendarDTO> calendarList;
    //private List<UserCalendar> relatedCalendarList;

    //private List<Team> teamList;
    //private List<ChatRoom> chatRoomList;
    //private List<ChatMessage> chatMessageList;
}
