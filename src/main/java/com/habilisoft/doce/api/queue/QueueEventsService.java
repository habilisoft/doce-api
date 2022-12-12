package com.habilisoft.doce.api.queue;

import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.model.TimeAttendanceRecord;
import com.habilisoft.doce.api.domain.repositories.ClientDeviceRepository;
import com.habilisoft.doce.api.domain.repositories.EmployeeRepository;
import com.habilisoft.doce.api.domain.services.DeviceService;
import com.habilisoft.doce.api.domain.services.EmployeeDeviceService;
import com.habilisoft.doce.api.domain.services.TimeAttendanceRecordService;
import com.habilisoft.doce.api.dto.device.QueueEvent;
import com.habilisoft.doce.api.dto.device.Disconnect;
import com.habilisoft.doce.api.dto.device.RegisterDevice;
import com.habilisoft.doce.api.dto.device.SendLogs;
import com.habilisoft.doce.api.dto.device.SendUserDataToDevice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 2019-04-21.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueueEventsService {
    private final TimeAttendanceRecordService timeAttendanceRecordService;
    private final EmployeeRepository employeeRepository;
    private final DeviceService deviceService;
    private final ClientDeviceRepository clientDeviceRepository;
    private final EmployeeDeviceService employeeDeviceService;
    private final Gson gson;

    public void processCommand(String message) {
        try {

            QueueEvent queueEvent = new ObjectMapper().readValue(message, QueueEvent.class);
            if(!setTenant(queueEvent.getDeviceSerialNumber())) {

                log.warn(
                        "Device not associated with a client {}",
                        kv("serialNumber",queueEvent.getDeviceSerialNumber()));
                return;
            }

            if (queueEvent.type != null) {
                switch (queueEvent.type) {
                    case REG:
                        registerDevice(message);
                        break;
                    case LOG:
                        saveLogRecords(message);
                        break;
                    case SEND_USER:
                        saveUserData(message);
                        break;
                    case DISCONNECT:
                        disconnect(message);
                        break;
                    case GET_USER_LIST:
                        System.out.println(message);
                        break;
                    case DEFAULT:
                        break;
                }

            } else if (queueEvent.returnValue != null) {
                switch (queueEvent.returnValue) {
                    case GET_USER_INFO:
                        saveUserData(message);
                        break;
                    case DEFAULT:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void registerDevice(String message) {
        try {
            RegisterDevice registerDevice = new ObjectMapper().readValue(message, RegisterDevice.class);
            deviceService.registerDevice(registerDevice);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(String message) {
        try {
            Disconnect disconnect = new ObjectMapper().readValue(message, Disconnect.class);
            deviceService.disconnect(disconnect);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private boolean setTenant(final String deviceSerialNumber) {
        TenantContext.setCurrentTenant(null);
        final String tenant = clientDeviceRepository.getClientTenantByDeviceSerialNumber(deviceSerialNumber);

        if(tenant == null) {
            return false;
        }

        TenantContext.setCurrentTenant(tenant);
        return true;
    }

    /*private void sendResponse(final WebSocketSession session, final Command.Type commandType) {
        sendResponse(session, commandType, true);
    }

    private void sendResponse(final WebSocketSession session, final Command.Type commandType, final Boolean result) {
        try {
            TextMessage textMessage = new TextMessage(
                    Response.builder()
                            .result(result)
                            .ret(commandType.value())
                            .cloudtime(simpleDateFormat.format(new Date()))
                            .build().toString()
            );

            session.sendMessage(textMessage);

            log.info(String.format("Message sent to Device %s: %s", session.getId(), textMessage.getPayload()));

        } catch (Exception e) {
            log.warning(String.format("Could not send response to server. Exception is: %s", e.getMessage()));
        }
    }*/


    private void saveLogRecords(String message) throws IOException {
        SendLogs sendLogs = this.gson.fromJson(message, SendLogs.class);

        List<TimeAttendanceRecord> records = sendLogs.getRecord()
                .stream()
                .filter(r -> employeeRepository.findByEnrollId(r.getEnrollId()).isPresent())
                .map(r -> {
                    TimeAttendanceRecord record = r.toTimeAttendanceRecord(sendLogs.getDeviceSerialNumber());
                    Employee employee = employeeRepository.findByEnrollId(r.getEnrollId()).get();
                    record.setEmployee(employee);

                    return record;
                })
                .collect(Collectors.toList());

        timeAttendanceRecordService.update(records);


       // final Device device = deviceService.getDevice(sendLogs.getDeviceSerialNumber());
        //final List<Employee> employees = records.stream().map(TimeAttendanceRecord::getEmployee).collect(Collectors.toList());

        //this.employeeService.validateEmployeeHaveFingerPrintData(device, employees);
    }

    private void saveUserData(String message) {

        try {
            SendUserDataToDevice sendUserDataToDevice = new ObjectMapper()
                    .readValue(message, SendUserDataToDevice.class);
            Employee employee = employeeDeviceService.saveEmployeeDeviceData(sendUserDataToDevice);
            System.out.println(employee);



            //deviceService.saveUser(sendUser, session);
            //sendResponse(session, Command.Type.SEND_USER);
            //deviceService.setUserName(session, sendUser.getEnrollId());
        } catch (Exception e) {
            log.warn(String.format("Could not send user data to device. Exception is: %s", e.getMessage()));
        }
    }

}
