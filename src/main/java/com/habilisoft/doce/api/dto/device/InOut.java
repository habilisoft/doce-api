package com.habilisoft.doce.api.dto.device;

import com.google.gson.annotations.SerializedName;

/**
 * Created on 2019-04-21.
 */
public enum InOut {

    @SerializedName("0")
    IN(0),

    @SerializedName("0")
    OUT(1);

    private final int value;

    public int value() {
        return this.value;
    }

    InOut(int value){
        this.value = value;
    }
}
