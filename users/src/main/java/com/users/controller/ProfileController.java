package com.users.controller;

import com.users.model.dto.user.UserProfileResponse;
import com.users.security.UserPrincipal;
import com.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public UserProfileResponse httpGetCurrentUserProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        return userService.getCurrentUserProfile(currentUser);
    }

    @GetMapping("internal")
    public UserProfileResponse httpGetCurrentUserProfile(@RequestHeader("user-id") Long userId) {
        return userService.getCurrentUserProfile(userId);
    }

}