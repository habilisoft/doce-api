package com.armando.timeattendance.api.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AmbulatoryAssistanceSummaryReport {

    private Long id;
    private String firstName;
    private String lastName;
    private Date recordDate;
    private Long visits;

    public String getName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
}
