package com.armando.timeattendance.api.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AmbulatoryAssistanceDetailReport {

    private Long id;
    private String firstName;
    private String lastName;
    private String LocationName;
    private String deviceSerialNumber;
    private Date recordDate;
    private Date time;

    public String getName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
}
