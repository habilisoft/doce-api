package com.armando.timeattendance.api.domain.services;

import com.armando.timeattendance.api.domain.events.EmployeeEditedEvent;
import com.armando.timeattendance.api.domain.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;
import java.util.Optional;

/**
 * Created on 11/11/22.
 */
@Service
public class EmployeeWorkShiftAuditService {

    @TransactionalEventListener
    public void onEmployeeEditedEvent(EmployeeEditedEvent event) {

    }

/*    public Employee createWorkShiftAudit(Employee employee, final Action action) {
        if (employee.getLocationType() == LocationType.FIXED) {

            Optional<EmployeeWorkShiftAudit> lastAuditOptional = this.employeeWorkShiftAuditRepository
                    .findEmployeeLastAuditEntry(employee);

            if (lastAuditOptional.isPresent()) {
                EmployeeWorkShiftAudit lastAudit = lastAuditOptional.get();
                lastAudit.setEndDate(new Date());
                this.employeeWorkShiftAuditRepository.save(lastAudit);
            }

            if (employee.getWorkShift() != null) {
                EmployeeWorkShiftAudit employeeWorkShiftAudit = EmployeeWorkShiftAudit.builder()
                        .employee(employee)
                        .workShift(employee.getWorkShift())
                        .startDate(new Date())
                        .action(action)
                        .build();

                this.employeeWorkShiftAuditRepository.save(employeeWorkShiftAudit);
            }
        }

        return employee;
    }*/
}
