package com.habilisoft.doce.api.web.events;

import com.habilisoft.doce.api.persistence.repositories.TimeAttendanceRecordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 14/12/22.
 */
@RestController
@RequestMapping("/events-log")
@RequiredArgsConstructor
public class EventsLogResource {
    private final TimeAttendanceRecordJpaRepository jpaRepository;


    @GetMapping
    public Page<?> searchEvents() {
        return null;
    }
}
