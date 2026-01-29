package com.orders.service.client.fallback;

import com.orders.error.exception.ServiceUnavailableException;
import com.orders.model.dto.feignclient.UserProfileResponse;
import com.orders.service.client.UserFeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserFallback implements UserFeignClient {
    @Override
    public UserProfileResponse httpGetCurrentUserProfile(Long userId) {
        throw new ServiceUnavailableException("user service", "an error happen please tray after some time");
    }
}
