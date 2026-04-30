package com.habilisoft.doce.api.web.users.resources;

import com.habilisoft.doce.api.auth.model.User;
import com.habilisoft.doce.api.auth.base.utils.SortUtils;
import com.habilisoft.doce.api.auth.services.UserService;
import com.habilisoft.doce.api.config.StartupHousekeeper;
import com.habilisoft.doce.api.web.users.dto.ChangePasswordRequest;
import com.habilisoft.doce.api.web.users.dto.CreateUserMaintenanceRequest;
import com.habilisoft.doce.api.web.users.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


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
                toResponse(user)
        );

    }

    @GetMapping
    public Page<UserResponse> searchUsers(@RequestParam final Map<String, Object> queryMap,
                                          @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                                          @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                                          @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {
        if (!StringUtils.hasLength(sort)) {
            sort = "+name";
        }

        return userService.searchUsers(
                queryMap,
                PageRequest.of(page, size, SortUtils.processSort(sort, new String[]{"name", "username", "lastLogin"}))
        ).map(this::toResponse);
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request.getOldPassword(), request.getNewPassword());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Validated CreateUserMaintenanceRequest request) {
        userService.createUser(request);
        return UserResponse.builder()
                .name(request.getName())
                .username(request.getUsername() != null ? request.getUsername().trim().toLowerCase() : null)
                .active(true)
                .build();
    }

    @GetMapping("/default-user")
    void du() {
        startupHousekeeper.createUser("superadmin");
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .profileImageUrl(user.getProfileImageUrl())
                .id(user.getId())
                .lastLogin(user.getLastLogin())
                .active(user.getActive())
                .build();
    }

}
