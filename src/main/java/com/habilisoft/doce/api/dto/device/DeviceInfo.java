package com.habilisoft.doce.api.dto.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Embeddable;
import java.util.Date;

@Data
@Embeddable
public class DeviceInfo {
    @JsonProperty(value = "modelname")
    private String modelName;
    @JsonProperty(value = "usersize")
    private Integer userSize;
    @JsonProperty(value = "fpsize")
    private Integer fpSize;
    @JsonProperty(value = "cardsize")
    private Integer cardSize;
    @JsonProperty(value = "pwdsize")
    private Integer pwdSize;
    @JsonProperty(value = "logsize")
    private Integer logSize;
    @JsonProperty(value = "useduser")
    private Integer usedUser;
    @JsonProperty(value = "usedfp")
    private Integer usedFp;
    @JsonProperty(value = "usedcard")
    private Integer usedCard;
    @JsonProperty(value = "usedpwd")
    private Integer usedPwd;
    @JsonProperty(value = "usedlog")
    private Integer usedLog;
    @JsonProperty(value = "usednewlog")
    private Integer usedNewLog;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String firmware;
    @JsonProperty(value = "fpalgo")
    private String fpAlgorithm;
    @JsonProperty(value = "falgo")
    private String faceAlgorithm;
    @JsonProperty(value = "facesize")
    private Integer faceSize;
    @JsonProperty(value = "usedface")
    private Integer usedFace;
    @JsonProperty(value = "netinuse")
    private Integer netInUse;
    private String mac;
}
