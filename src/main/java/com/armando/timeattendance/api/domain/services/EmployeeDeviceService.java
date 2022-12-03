package com.armando.timeattendance.api.domain.services;

import com.armando.timeattendance.api.config.multitenant.TenantContext;
import com.armando.timeattendance.api.domain.events.EmployeeCreatedEvent;
import com.armando.timeattendance.api.domain.events.EmployeeEditedEvent;
import com.armando.timeattendance.api.domain.exceptions.EmployeeNotFoundException;
import com.armando.timeattendance.api.domain.model.Device;
import com.armando.timeattendance.api.domain.model.Employee;
import com.armando.timeattendance.api.domain.repositories.DeviceRepository;
import com.armando.timeattendance.api.domain.repositories.EmployeeRepository;
import com.armando.timeattendance.api.dto.device.DeleteUser;
import com.armando.timeattendance.api.dto.device.SendUserToAllDevices;
import com.armando.timeattendance.api.dto.device.SendUserDataToDevice;
import com.armando.timeattendance.api.queue.SqsQueueSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 11/11/22.
 */
@Component
@Slf4j
public class EmployeeDeviceService {
    private final DeviceRepository deviceRepository;
    private final EmployeeRepository employeeRepository;
    private final SqsQueueSender queueSender;

    public EmployeeDeviceService(final DeviceRepository deviceRepository,
                                 final EmployeeRepository employeeRepository,
                                 final SqsQueueSender queueSender) {
        this.deviceRepository = deviceRepository;
        this.employeeRepository = employeeRepository;
        this.queueSender = queueSender;
    }

    @TransactionalEventListener
    public void onEmployeeEditedEvent(EmployeeEditedEvent event) {
        Employee oldData = event.getOldEmployee();
        Employee newData = event.getNewEmployee();
        log.info("Received employee edited event {} {}", kv("newValue", newData), kv("oldValue", oldData));
        switch (newData.getLocationType()) {
            case FIXED -> {
                if(!oldData.getLocation().equals(newData.getLocation())) {
                    removeEmployeeDataFromDevice(oldData);
                    sendEmployeeDataToDevice(newData);
                }
            }
            case AMBULATORY -> sendEmployeeDataToAllDevices(newData);
        }
    }

    @TransactionalEventListener
    public void onEmployeeCreatedEvent(EmployeeCreatedEvent event) {
        Employee employee = event.getEmployee();

        log.info("Received employee created event {}", kv("employee", employee));

        switch (employee.getLocationType()) {
            case FIXED -> sendEmployeeDataToDevice(employee);
            case AMBULATORY -> sendEmployeeDataToAllDevices(employee);
        }
    }

    public void sendEmployeeDataToDevice(final Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        sendEmployeeDataToDevice(employee);
    }

    public void sendEmployeeDataToDevice(final Employee employee) {
        Device device = deviceRepository.findByLocation(employee.getLocation())
                        .orElse(null);
        if(device == null) {
            sendEmployeeDataToAllDevices(employee);
        } else {
            sendEmployeeDataToDevice(employee, device);
        }
    }

    public void sendEmployeeDataToDevice(final Employee emp, final Device device) {
        if(device == null) {
            log.warn("Could not sent employee to empty device {}", kv("employee", emp));
            return;
        }

        SendUserDataToDevice command = SendUserDataToDevice.builder()
                .enrollId(emp.getEnrollId())
                .name(emp.getFullName())
                .record(emp.getFingerprintData())
                .backupNum(0)
                .deviceId(device.getSerialNumber())
                .admin(0)
                .build();
        log.info("Sending employee data to device {} {}", kv("employeeId", emp.getId()), kv("deviceId", device.getSerialNumber()));
        queueSender.convertAndSend(command);
    }

    public void sendEmployeeDataToAllDevices(Employee emp) {
        String clientId = TenantContext.getCurrentTenant();
        SendUserToAllDevices command = SendUserToAllDevices.builder()
                .enrollId(emp.getEnrollId())
                .name(emp.getFullName())
                .record(emp.getFingerprintData())
                .backupNum(0)
                .clientId(clientId)
                .admin(0)
                .build();
        log.info("Sending employee data to all devices {} {}", kv("employeeId", emp.getId()), kv("clientId", clientId));
        queueSender.convertAndSend(command);
    }

    public void removeEmployeeDataFromDevice(Employee emp) {
        Device device = deviceRepository.findByLocation(emp.getLocation())
                .orElse(null);

        if(device == null) {
            log.warn("Could not sent employee to empty device {}", kv("employee", emp));
            return;
        }

        String clientId = TenantContext.getCurrentTenant();
        DeleteUser deleteUser = DeleteUser.builder()
                .enrollId(emp.getEnrollId())
                .deviceId(device.getSerialNumber())
                .build();
        log.info("Sending employee data to all devices {} {}", kv("employeeId", emp.getId()), kv("clientId", clientId));
        queueSender.convertAndSend(deleteUser);
    }

    public void registerEmployee() {

    }

    @Transactional
    public Employee saveEmployeeDeviceData(SendUserDataToDevice sendUserDataToDevice) {
        Integer enrollId = sendUserDataToDevice.getEnrollId();
        String fpData = sendUserDataToDevice.getRecord();
        Employee employee = employeeRepository.findByEnrollId(enrollId)
                .orElseThrow();
        employee.setFingerprintData(fpData);
        employeeRepository.save(employee);
        return employee;
    }
}
