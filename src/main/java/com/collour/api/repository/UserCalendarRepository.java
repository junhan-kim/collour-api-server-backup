package com.collour.api.repository;

import com.collour.api.domain.Calendar;
import com.collour.api.domain.UserCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCalendarRepository extends JpaRepository<UserCalendar, Long> {

    UserCalendar save(UserCalendar userCalendar);

    void deleteAllByCalendar(Calendar calendar);
}
