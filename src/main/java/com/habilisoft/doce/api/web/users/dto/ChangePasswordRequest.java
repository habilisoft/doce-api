package com.habilisoft.doce.api.web.users.dto;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Created on 2020-11-24.
 */
@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
