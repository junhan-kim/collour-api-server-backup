package com.collour.api.repository;

import com.collour.api.domain.Calendar;
import com.collour.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Calendar findByNo(Long no);

    List<Calendar> findAllByAuthorOrderByStartDateAsc(User author);
}
