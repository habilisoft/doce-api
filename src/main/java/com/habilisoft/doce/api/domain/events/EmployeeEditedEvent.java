package com.habilisoft.doce.api.domain.events;

import com.habilisoft.doce.api.domain.model.Employee;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Created on 11/11/22.
 */
@Data
public class EmployeeEditedEvent extends ApplicationEvent {
    private Employee oldEmployee;
    private Employee newEmployee;

    @Builder
    public EmployeeEditedEvent(Employee oldEmployee, Employee newEmployee, Object source) {
        super(source);
        this.newEmployee = newEmployee;
        this.oldEmployee = oldEmployee;
    }
}
