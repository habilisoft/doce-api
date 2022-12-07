package com.habilisoft.doce.api.auth.dto;

import lombok.Data;


/**
 * Created on 2020-11-24.
 */
@Data
public class RecoverChangePasswordRequest {
    private String token;
    private String newPassword;
}
