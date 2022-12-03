package com.armando.timeattendance.api.domain.events;

import com.armando.timeattendance.api.domain.model.Employee;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Created on 11/11/22.
 */
@Data
public class EmployeeCreatedEvent extends ApplicationEvent {
    private Employee employee;
    @Builder
    public EmployeeCreatedEvent(Employee employee, Object source) {
        super(source);
        this.employee = employee;
    }
}
