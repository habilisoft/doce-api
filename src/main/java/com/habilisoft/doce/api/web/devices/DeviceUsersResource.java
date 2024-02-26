package com.habilisoft.doce.api.web.devices;

import com.habilisoft.doce.api.device_service.DeviceServiceWebClient;
import com.habilisoft.doce.api.device_service.dto.EmployeeDataResponse;
import com.habilisoft.doce.api.device_service.dto.GetEmployeeDataRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 3/3/23.
 */
@RestController
@RequestMapping("/devices/{deviceSerialNumber}/users")
@RequiredArgsConstructor
public class DeviceUsersResource {
    private final DeviceServiceWebClient deviceServiceWebClient;

    @GetMapping("/{enrollId}&backUpNum={backUpNumber}")
       public ResponseEntity<?> getEmployeeData(@PathVariable String deviceSerialNumber,
                                                @PathVariable String enrollId,
                                                @PathVariable Integer backUpNumber) {

        GetEmployeeDataRequest request = GetEmployeeDataRequest.builder()
                .deviceSerialNumber(deviceSerialNumber)
                .enrollId(enrollId)
                .backUpNumber(backUpNumber)
                .build();

        EmployeeDataResponse response = deviceServiceWebClient.getEmployeeDataFromDevice(request);

        return ResponseEntity.ok(response);
    }
}
