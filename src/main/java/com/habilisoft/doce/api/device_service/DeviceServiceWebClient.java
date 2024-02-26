package com.habilisoft.doce.api.device_service;

import com.habilisoft.doce.api.device_service.dto.EmployeeDataResponse;
import com.habilisoft.doce.api.device_service.dto.GetEmployeeDataRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created on 17/12/22.
 */
@Service
@RequiredArgsConstructor
public class DeviceServiceWebClient {
    private final RestTemplate deviceServiceRestTemplate;

    public EmployeeDataResponse getEmployeeDataFromDevice(GetEmployeeDataRequest request) {
        String url = String.format(
                "/devices/%s/users/%s&backUpNum=%s",
                request.getDeviceSerialNumber(),
                request.getEnrollId(),
                request.getBackUpNumber());

        return deviceServiceRestTemplate
                .getForObject(url, EmployeeDataResponse.class);
    }

    public void getDeviceInfo() {

    }


    public void setDeviceInfo() {

    }

}
