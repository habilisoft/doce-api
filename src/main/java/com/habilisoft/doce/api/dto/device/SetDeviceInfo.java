package com.habilisoft.doce.api.dto.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetDeviceInfo extends QueueEvent {

    @JsonProperty("deviceid")
    private Long deviceId;

    //Default 0 -> English
    private DeviceLanguage language = DeviceLanguage.ENGLISH;

    //Volume from 0 to 10, default 6
    private Integer volume = 6;

    //Screensaver seconds  0 no screensaver from 1 to 255 seconds, default 0
    @JsonProperty("screensaver")
    private Integer screenSaver = 0;

    @JsonProperty("verifymode")
    private VerifyMode verifyMode;

    //Default 0 No sleep
    private SleepType sleep = SleepType.NO_SLEEP;

    //Defined how many fingerprints per user 1 to 10, default 3
    @JsonProperty("userfpnum")
    private Integer userFingerPrintNumber = 3;

    //when the logs will full less then 1000 ,and the terminal will hint;0:no hint
    @JsonProperty("loghint")
    private Integer logHint = 1000;

    //re verify time 0 to 255 minute
    @JsonProperty("reverifytime")
    private Integer reVerifyTime = 0;

    @Builder
    public SetDeviceInfo(Long deviceId, DeviceLanguage language, Integer volume, Integer screenSaver, VerifyMode verifyMode, SleepType sleep, Integer userFingerPrintNumber, Integer logHint, Integer reVerifyTime) {
        this.deviceId = deviceId;
        this.language = language;
        this.volume = volume;
        this.screenSaver = screenSaver;
        this.verifyMode = verifyMode;
        this.sleep = sleep;
        this.userFingerPrintNumber = userFingerPrintNumber;
        this.logHint = logHint;
        this.reVerifyTime = reVerifyTime;
        this.type = QueueEvent.Type.SET_DEVICE_INFO;
    }
}
