package com.habilisoft.doce.api.web.users.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateUserMaintenanceRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 3, max = 255)
    private String username;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
}
