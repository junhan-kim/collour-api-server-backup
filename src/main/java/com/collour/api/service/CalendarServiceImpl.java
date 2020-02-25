package com.collour.api.service;

import com.collour.api.domain.Calendar;
import com.collour.api.domain.User;
import com.collour.api.dto.CalendarDTO;
import com.collour.api.dto.CalendarMapper;
import com.collour.api.dto.ExceptionDTO;
import com.collour.api.repository.CalendarRepository;
import com.collour.api.repository.UserCalendarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private CalendarRepository calendarRepository;
    private CalendarMapper calendarMapper;
    private JwtUserDetailsService jwtUserDetailsService;
    private UserCalendarRepository userCalendarRepository;

    @Override
    public Calendar findCalendar(Long no) throws ExceptionDTO {
        Calendar calendar = calendarRepository.findByNo(no);
        if (calendar == null) throw new ExceptionDTO(ExceptionDTO.ErrorCode.CALENDAR_NOT_FOUND);
        return calendar;
    }

    @Override
    public void create(CalendarDTO calendarDTO) throws Exception {
        for(String username : calendarDTO.getRelatedUsernameList()){
            jwtUserDetailsService.findUser(username);  // check users are exist
        }
        calendarRepository.save(calendarMapper.toCalendar(calendarDTO, calendarRepository, jwtUserDetailsService, userCalendarRepository));
    }

    @Override
    public List<CalendarDTO> readAll(String username) throws Exception {
        User user = jwtUserDetailsService.findUser(username);
        List<CalendarDTO> calendarDTOList = new ArrayList<>();
        for(Calendar calendar : calendarRepository.findAllByAuthorOrderByStartDateAsc(user)){
            calendarDTOList.add(calendarMapper.toBriefCalendarDTO(calendar));
        }
        return calendarDTOList;
    }

    @Override
    public CalendarDTO read(Long no) throws ExceptionDTO {
        return calendarMapper.toDetailedCalendarDTO(findCalendar(no));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long no, CalendarDTO calendarDTO) throws Exception {
        Calendar calendar = findCalendar(no);
        calendarMapper.updateCalendar(calendarDTO, calendar, jwtUserDetailsService, userCalendarRepository);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long no) throws ExceptionDTO {
        Calendar calendar = findCalendar(no);
        userCalendarRepository.deleteAllByCalendar(calendar);
        calendarRepository.delete(calendar);
    }


    // ------------------ method for team

    //public List<CalendarDTO> readAllByMonth readAll(String username, Date date)
    //public List<CalendarDTO> readAll(Long teamNo){}
    //public List<CalendarDTO> readAll(
}
