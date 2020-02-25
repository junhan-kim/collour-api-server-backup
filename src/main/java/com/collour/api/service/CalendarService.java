package com.collour.api.service;

import com.collour.api.domain.Calendar;
import com.collour.api.dto.CalendarDTO;
import com.collour.api.dto.ExceptionDTO;

import java.util.List;

public interface CalendarService {

    Calendar findCalendar(Long no) throws ExceptionDTO;

    void create(CalendarDTO calendarDTO) throws Exception;

    List<CalendarDTO> readAll(String username) throws Exception;

    CalendarDTO read(Long no) throws ExceptionDTO;

    void update(Long no, CalendarDTO calendarDTO) throws Exception;

    void delete(Long no) throws ExceptionDTO;
}
