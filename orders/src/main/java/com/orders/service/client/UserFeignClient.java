package com.orders.service.client;

import com.orders.model.dto.feignclient.UserProfileResponse;
import com.orders.service.client.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "users", fallback =  UserFallback.class)
public interface UserFeignClient {
    @GetMapping("profile/internal")
    UserProfileResponse httpGetCurrentUserProfile(@RequestHeader("user-id") Long userId);
}
