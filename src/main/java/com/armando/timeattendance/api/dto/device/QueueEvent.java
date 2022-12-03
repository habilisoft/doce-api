package com.armando.timeattendance.api.dto.device;

import com.armando.timeattendance.api.serialization.CommandTypeConverter;
import com.armando.timeattendance.api.serialization.CommandTypeToStringConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Arrays;

/**
 * Created on 2019-05-07.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueEvent {

    @JsonDeserialize(converter = CommandTypeConverter.class)
    @JsonSerialize(converter = CommandTypeToStringConverter.class)
    @JsonProperty(value = "cmd")
    public Type type;

    @JsonDeserialize(converter = CommandTypeConverter.class)
    @JsonSerialize(converter = CommandTypeToStringConverter.class)
    @JsonProperty(value = "ret")
    public Type returnValue;

    @SerializedName("sn")
    @JsonProperty(value = "sn")
    public String deviceSerialNumber;

    public String toJsonString(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        }catch (Exception e){
            return  null;
        }
    }

    /**
     * Created on 2019-04-21.
     */
    public enum Type {
        //Default device events
        REG("reg"),
        LOG("sendlog"),
        DEFAULT("default"),
        SEND_USER("senduser"),
        SET_USER_INFO("setuserinfo"),
        GET_USER_LIST("getuserlist"),
        DELETE_USER("deleteuser"),
        SET_TIME("settime"),
        SEND_USER_DATA_TO_DEVICE("sendUserDataToDevice"),
        SEND_USER_TO_ALL_DEVICES("sendUserToAllDevices"),
        SET_USER_NAME("setusername"),
        SET_DEVICE_INFO("setdevinfo"),
        GET_USER_INFO("getuserinfo"),
        //Custom events
        DISCONNECT("disconnect");

        private final String value;

        public String value() {
            return this.value;
        }

        Type(String value){
            this.value = value;
        }

        public static Type getByValue(String value){
            return switch (value) {
                case "reg" -> REG;
                case "sendlog" -> LOG;
                case "senduser" -> SEND_USER;
                case "settime" -> SET_TIME;
                case "setuserinfo" -> SET_USER_INFO;
                case "deleteuser" -> DELETE_USER;
                case "setusername" -> SET_USER_NAME;
                case "setdevinfo" -> SET_DEVICE_INFO;
                case "getuserinfo" -> GET_USER_INFO;
                case "sendUserToDevice" -> SEND_USER_DATA_TO_DEVICE;
                case "sendUserToAllDevices" -> SEND_USER_TO_ALL_DEVICES;
                case "disconnect" -> DISCONNECT;
                default ->
                        throw new IllegalArgumentException(String.format("Invalid Command [%s]. Valid commands are %s", value, Arrays.toString(Type.values())));
            };
        }

        @Override
        public String toString() {
            return value;
        }}
}
