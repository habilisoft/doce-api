package com.habilisoft.doce.api.domain.services;

import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.domain.events.EmployeeCreatedEvent;
import com.habilisoft.doce.api.domain.events.EmployeeEditedEvent;
import com.habilisoft.doce.api.domain.exceptions.EmployeeNotFoundException;
import com.habilisoft.doce.api.domain.model.Device;
import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.repositories.DeviceRepository;
import com.habilisoft.doce.api.domain.repositories.EmployeeRepository;
import com.habilisoft.doce.api.dto.device.DeleteUser;
import com.habilisoft.doce.api.dto.device.SendUserToAllDevices;
import com.habilisoft.doce.api.dto.device.SendUserDataToDevice;
import com.habilisoft.doce.api.persistence.converters.EmployeeJpaConverter;
import com.habilisoft.doce.api.persistence.repositories.EmployeeJpaRepo;
import com.habilisoft.doce.api.queue.SqsQueueSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.e;
import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 11/11/22.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeDeviceService {
    private final DeviceRepository deviceRepository;
    private final EmployeeRepository employeeRepository;
    private final SqsQueueSender queueSender;
    private final EmployeeJpaRepo employeeJpaRepo;
    private final EmployeeJpaConverter employeeJpaConverter;
    private final ApplicationEventPublisher eventPublisher;

    @TransactionalEventListener
    public void onEmployeeEditedEvent(EmployeeEditedEvent event) {
        Employee oldData = event.getOldEmployee();
        Employee newData = event.getNewEmployee();
        log.info("Received employee edited event {} {}", kv("newValue", newData), kv("oldValue", oldData));
        sendEmployeeDataToAllDevices(newData);
    }

    @TransactionalEventListener
    public void onEmployeeCreatedEvent(EmployeeCreatedEvent event) {
        Employee employee = event.getEmployee();
        log.info("Received employee created event {}", kv("employee", employee));
        sendEmployeeDataToAllDevices(employee);
    }

    public void sendEmployeeDataToDevice(final Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        sendEmployeeDataToDevice(employee);
    }

    public void sendEmployeeDataToDevice(final Employee employee) {
        if(employee.getLocation() == null) {
            sendEmployeeDataToAllDevices(employee);
            return;
        }
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

    @Transactional
    public Employee saveEmployeeDeviceData(SendUserDataToDevice sendUserDataToDevice) {
        Integer enrollId = sendUserDataToDevice.getEnrollId();
        String fpData = sendUserDataToDevice.getRecord();
        Employee employee = employeeRepository.findByEnrollId(enrollId)
                .orElse(Employee.builder()
                        .enrollId(enrollId)
                        .build());
        employee.setFingerprintData(fpData);
        employeeRepository.save(employee);

        sendEmployeeDataToAllDevices(employee);

        return employee;
    }

    @Transactional
    public void sendAllEmployeeDataDevice(Device device) {
        employeeJpaRepo.streamAllBy()
                .forEach(employee -> sendEmployeeDataToDevice(employeeJpaConverter.fromJpaEntity(employee), device));
    }

    @Transactional
    public void sendAllEmployeeDataDevice(Device device, String tenant) {
        TenantContext.setCurrentTenant(tenant);
        employeeJpaRepo.streamAllBy()
                .forEach(employee -> sendEmployeeDataToDevice(employeeJpaConverter.fromJpaEntity(employee), device));
    }

    @Transactional
    public void sendEmployeeFpDataToDevice(Device device) {
        employeeJpaRepo.streamAllByFingerprintDataIsNotNull()
                .forEach(employee -> sendEmployeeDataToDevice(employeeJpaConverter.fromJpaEntity(employee), device));
    }

    @Transactional
    public void sendEmployeeFpDataToDevice(Device device, List<Integer> enrollIds) {
        employeeJpaRepo.streamAllByFingerprintDataIsNotNullAndEnrollIdIn(enrollIds)
                .forEach(employee -> sendEmployeeDataToDevice(employeeJpaConverter.fromJpaEntity(employee), device));
    }
}
