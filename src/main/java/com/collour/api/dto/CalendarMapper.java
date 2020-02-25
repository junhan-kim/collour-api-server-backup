package com.collour.api.dto;

import com.collour.api.domain.Calendar;
import com.collour.api.domain.User;
import com.collour.api.domain.UserCalendar;
import com.collour.api.repository.CalendarRepository;
import com.collour.api.repository.UserCalendarRepository;
import com.collour.api.service.JwtUserDetailsService;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CalendarMapper {

    default Calendar toCalendar(CalendarDTO calendarDTO, @Context CalendarRepository calendarRepository, @Context JwtUserDetailsService jwtUserDetailsService, @Context UserCalendarRepository userCalendarRepository) throws Exception {
        if(calendarDTO == null) return null;
        Calendar calendar = new Calendar();
        calendar.setTitle(calendarDTO.getTitle());
        User user = jwtUserDetailsService.findUser(calendarDTO.getAuthorName());
        calendar.setAuthor(user);
        //calendar.setTeam()
        calendar.setStartDate(calendarDTO.getStartDate());
        calendar.setEndDate(calendarDTO.getEndDate());
        calendar.setContent(calendarDTO.getContent());
        calendarRepository.save(calendar);   // Immune transient property exception
        if(calendarDTO.getRelatedUsernameList() != null) {
            for (String username : calendarDTO.getRelatedUsernameList()) {
                userCalendarRepository.save(new UserCalendar(jwtUserDetailsService.findUser(username), calendar));
            }
        }
        return calendar;
    }

    @Mapping(target = "authorName", ignore = true)
    @Mapping(target = "teamNo", ignore = true)
    @Mapping(target = "relatedUsernameList", ignore = true)
    @Mapping(target = "content", ignore = true)
    CalendarDTO toBriefCalendarDTO(Calendar calendar);

    default CalendarDTO toDetailedCalendarDTO(Calendar calendar){
        if(calendar == null) return null;
        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setAuthorName(calendar.getAuthor().getUsername());
        calendarDTO.setRelatedUsernameList(
                calendar.getRelatedUserList().stream()
                .map(UserCalendar::getUser)
                .map(User::getUsername)
                .collect(Collectors.toList()));
        calendarDTO.setContent(calendar.getContent());
        return calendarDTO;
    }


    @Mapping(target = "no", ignore = true)
    @Mapping(target = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "relatedUserList", ignore = true)
    @Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "startDate", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "endDate", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateCalendar(CalendarDTO calendarDTO, @MappingTarget Calendar calendar, @Context JwtUserDetailsService jwtUserDetailsService, @Context UserCalendarRepository userCalendarRepository) throws Exception;

    @AfterMapping
    default void afterCalendarMapping(CalendarDTO calendarDTO, @MappingTarget Calendar calendar, @Context JwtUserDetailsService jwtUserDetailsService, @Context UserCalendarRepository userCalendarRepository) throws Exception {
        userCalendarRepository.deleteAllByCalendar(calendar);
        if(calendarDTO.getRelatedUsernameList() != null) {
            for (String username : calendarDTO.getRelatedUsernameList()) {
                userCalendarRepository.save(new UserCalendar(jwtUserDetailsService.findUser(username), calendar));
            }
        }
    }
}
