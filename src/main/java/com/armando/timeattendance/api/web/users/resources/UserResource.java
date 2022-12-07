package com.armando.timeattendance.api.web.users.resources;

import com.armando.timeattendance.api.auth.model.User;
import com.armando.timeattendance.api.auth.repositories.UserRepository;
import com.armando.timeattendance.api.auth.services.UserService;
import com.armando.timeattendance.api.config.StartupHousekeeper;
import com.armando.timeattendance.api.web.users.dto.ChangePasswordRequest;
import com.armando.timeattendance.api.web.users.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


/**
 * Created on 2020-08-04.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final StartupHousekeeper startupHousekeeper;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                UserResponse.builder()
                        .name(user.getName())
                        .username(user.getUsername())
                        .profileImageUrl(user.getProfileImageUrl())
                        .id(user.getId())
                        .lastLogin(user.getLastLogin())
                        .build()
        );

    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request.getOldPassword(), request.getNewPassword());
    }

    @GetMapping("/default-user")
    void du() {
        startupHousekeeper.createUser("superadmin");
    }

}
