package com.armando.timeattendance.api.domain.model;

/**
 * Created on 3/12/22.
 */
public enum Report {
    WORKED_HOURS("Horas trabajadas");
    final String name;

    Report(String name) {
        this.name = name;
    }
}
