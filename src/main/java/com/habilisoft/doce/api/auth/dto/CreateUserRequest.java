package com.habilisoft.doce.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created on 2020-08-03.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String email;
    private String password;
    private Boolean verified;
    private Boolean active;
    private String name;
    private String activationToken;
    private String timeZone;
    private String profileImageUrl;
    private Map<String, Object> metadata;
}
