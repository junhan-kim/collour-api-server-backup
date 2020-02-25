package com.collour.api.controller;

import com.collour.api.dto.CalendarDTO;
import com.collour.api.dto.ExceptionDTO;
import com.collour.api.service.CalendarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/calendars")
@AllArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping(value = "")
    public ResponseEntity<?> createCalendar(@RequestBody CalendarDTO calendarDTO) throws Exception {
        calendarService.create(calendarDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{no}")
    public ResponseEntity<CalendarDTO> readCalendar(@PathVariable Long no) throws ExceptionDTO {
        return ResponseEntity.ok(calendarService.read(no));
    }

    @GetMapping
    public ResponseEntity<List<CalendarDTO>> readCalendarList(@RequestParam("username") String username) throws Exception {
        return ResponseEntity.ok(calendarService.readAll(username));
    }


    @PutMapping(value = "/{no}")
    public ResponseEntity<?> updateCalendar(@PathVariable Long no, @RequestBody CalendarDTO calendarDTO) throws Exception {
        calendarService.update(no, calendarDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{no}")
    public ResponseEntity<?> deleteCalendar(@PathVariable Long no) throws Exception {
        calendarService.delete(no);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
