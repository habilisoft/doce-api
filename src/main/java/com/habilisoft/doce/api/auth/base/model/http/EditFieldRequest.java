package com.habilisoft.doce.api.auth.base.model.http;

import lombok.Data;

/**
 * Created on 2020-07-11.
 */
@Data
public class EditFieldRequest {
    private String fieldName;
    private Object value;
}
