package com.orders.service.client;

import com.orders.model.dto.feignclient.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("users")
public interface UserFeignClient {
    @GetMapping("profile/internal")
    UserProfileResponse httpGetCurrentUserProfile(@RequestHeader("user-id") Long userId);
}
