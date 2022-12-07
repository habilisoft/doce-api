package com.habilisoft.doce.api.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class EmployeesWorkHourReport {
    private Long id;
    private String firstName;
    private String lastName;
    private Date recordDate;
    private Date workTime;
    private String typeHours;
    private String totalWorkTime;

    public String getName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
}
